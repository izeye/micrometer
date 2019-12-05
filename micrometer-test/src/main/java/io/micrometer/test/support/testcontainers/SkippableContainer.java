/**
 * Copyright 2019 Pivotal Software, Inc.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.micrometer.test.support.testcontainers;

import org.junit.jupiter.api.Assumptions;
import org.testcontainers.DockerClientFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.lifecycle.Startable;

import java.util.function.Supplier;

/**
 * A {@link GenericContainer} decorator that skips test execution when Docker is not
 * available.
 *
 * @param <T> type of the underlying container
 * @author Andy Wilkinson
 * @author Madhura Bhave
 */
public class SkippableContainer<T extends GenericContainer> implements Startable {

    private final Supplier<T> containerFactory;

    private T container;

    public SkippableContainer(Supplier<T> containerFactory) {
        this.containerFactory = containerFactory;
    }

    public T getContainer() {
        if (this.container == null) {
            throw new IllegalStateException(
                    "Container cannot be accessed prior to test invocation");
        }
        return this.container;
    }

    @Override
    public void start() {
        Assumptions.assumeTrue(isDockerRunning(),
                "Could not find valid docker environment.");
        this.container = this.containerFactory.get();
        this.container.start();
    }

    private boolean isDockerRunning() {
        try {
            DockerClientFactory.instance().client();
            return true;
        }
        catch (Throwable ex) {
            return false;
        }
    }

    @Override
    public void stop() {
        if (this.container != null) {
            this.container.stop();
        }
    }

}
