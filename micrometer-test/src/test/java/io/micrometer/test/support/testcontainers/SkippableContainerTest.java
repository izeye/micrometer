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

import org.junit.jupiter.api.Test;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link SkippableContainer}.
 *
 * @author Johnny Lim
 */
@Testcontainers
class SkippableContainerTest {

    @Container
    private static final SkippableContainer<ElasticsearchContainer> CONTAINER = new SkippableContainer<>(
            () -> new ElasticsearchContainer("docker.elastic.co/elasticsearch/elasticsearch:6.4.1"));

    @Test
    void test() {
        assertThat(CONTAINER.getContainer().isRunning()).isTrue();
    }

}
