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

package sebastien.callier.serialization.codec.primitive;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import sebastien.callier.serialization.codec.CodecCache;
import sebastien.callier.serialization.deserializer.Deserializer;
import sebastien.callier.serialization.deserializer.DeserializerFactory;
import sebastien.callier.serialization.serializer.Serializer;
import sebastien.callier.serialization.serializer.SerializerFactory;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Sebastien Callier
 * @since 2017
 */
public class BooleanCodecTest {
    private static final BooleanCodec codec = new BooleanCodec((byte) 0);
    private static final BooleanCodec invalidCodec = new BooleanCodec((byte) 50);

    private static SerializerFactory serializerFactory;
    private static DeserializerFactory deserializerFactory;

    @BeforeClass
    public static void prepare() {
        CodecCache cache = new CodecCache();
        cache.register(codec);
        serializerFactory = new SerializerFactory(cache);
        deserializerFactory = new DeserializerFactory(cache);
    }

    @Test
    public void testWideRange() throws IOException {
        testBoolean(null);
        testBoolean(true);
        testBoolean(false);
    }

    @Test
    public void testInvalidData() throws IOException {
        Serializer serializer = serializerFactory.newSerializer();
        serializer.append(true, invalidCodec);
        serializer.close();
        try (Deserializer deserializer = deserializerFactory.newDeserializer(
                serializer.getByteArray(),
                0,
                serializer.currentSize())) {
            deserializer.read(codec);
            Assert.fail("missing exception");
        } catch (IOException e) {
            //expected
        }
    }

    private void testBoolean(Boolean value) throws IOException {
        Serializer serializer = serializerFactory.newSerializer();
        serializer.append(value, codec);
        serializer.append(value);
        serializer.close();

        try (Deserializer deserializer = deserializerFactory.newDeserializer(
                serializer.getByteArray(),
                0,
                serializer.currentSize())) {
            assertThat(deserializer.read(), is(value));
            assertThat(deserializer.read(codec), is(value));
            assertThat(deserializer.available(), is(0));
        }
    }
}

