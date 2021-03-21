/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.ignite.thick.models.datatypes;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by gridgain on 23.03.2016.
 */
public class JoinTypes extends Timestamped {
    /**
     * Data Long.
     */
    @QuerySqlField(index = true)
    private Long longCol;

    /**
     * Data double.
     */
    @QuerySqlField(index = true)
    private double doubleCol;

    /**
     * Data String.
     */
    @QuerySqlField(index = true)
    private String strCol;

    /**
     * Data boolean.
     */
    private boolean booleanCol;

    /**
     * Data int.
     */
    @QuerySqlField(index = true)
    private int intCol;

    /**
     * Data bytes array.
     */
    private byte[] bytesCol;

    /**
     * Inner types column.
     */
    private InnerTypes innerTypesCol;

    /**
     * Inner Type
     */
    private static class InnerTypes extends Timestamped {
        /**
         * Long column.
         */
        private Long longCol;

        /**
         * @param key Key.
         */
        InnerTypes(Long key) {
            longCol = key;

        }

        /** {@inheritDoc} */
        @Override
        public String toString() {
            return "InnerTypes [Long=" + longCol + "]";
        }
    }

    /**
     * Enum type
     */
    private enum enumType {
        ENUMTRUE, ENUMFALSE
    }

    /**
     * Enum column.
     */
    private enumType enumCol;

    /**
     * Map column.
     */
    private ConcurrentMap<String, String> mapCol = new ConcurrentHashMap<>();

    /**
     * @param key key
     * @param str string value
     */
    private void init(Long key, String str) {
        longCol = key;
        doubleCol = key.doubleValue() / 100;
        strCol = str;
        if (key % 2 == 0) {
            booleanCol = true;
            enumCol = enumType.ENUMTRUE;
        } else {
            booleanCol = false;
            enumCol = enumType.ENUMFALSE;
        }
        intCol = key.intValue();
        bytesCol = strCol.getBytes();
        innerTypesCol = new InnerTypes(key);
        mapCol.put("map_key_" + key, "map_value_" + key);
        mapCol.put("map_key_" + (key + 1), "map_value_" + (key + 1));
    }

    /**
     * @param key Key.
     */
    public JoinTypes(Long key) {
        init(key, Long.toString(key));
    }

    /**
     * @param key Key.
     * @param str String.
     */
    public JoinTypes(Long key, String str) {
        init(key, str);
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "JoinTypes [Long=" + longCol +
                ", double=" + doubleCol +
                ", String='" + strCol +
                "', boolean=" + booleanCol +
                ", int=" + intCol +
                ", bytes='" + Arrays.toString(bytesCol) +
                "', InnerTypes=" + innerTypesCol.toString() +
                "', Map='" + mapCol.toString() +
                "', enum='" + enumCol.toString() +
                "']";
    }

}
