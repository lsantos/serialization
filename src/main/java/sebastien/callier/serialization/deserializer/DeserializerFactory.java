/*
 * Copyright 2017 Sebastien Callier
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package sebastien.callier.serialization.deserializer;

import sebastien.callier.serialization.codec.CodecCache;

/**
 * @author Sebastien Callier
 * @since 2017
 */
public class DeserializerFactory {
    private final CodecCache codecCache;

    public DeserializerFactory(CodecCache codecCache) {
        super();
        this.codecCache = codecCache;
    }

    public Deserializer newDeserializer(
            byte[] data,
            int offset,
            int length) {
        return Deserializer.newInstance(
                codecCache,
                data,
                offset,
                length);
    }
}
