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

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by gridgain on 23.03.2016.
 */
public class AllTypes extends Timestamped {
    /**
     * Data Long.
     */
    private Long longCol;

    /**
     * Data double.
     */
    private double doubleCol;

    /**
     * Data String.
     */
    private String strCol;

    /**
     * Data boolean.
     */
    private boolean booleanCol;

    /**
     * Data int.
     */
    private int intCol;

    /**
     * BigDecimal
     */
    private BigDecimal bigDecimalCol;

    /**
     * Data bytes array.
     */
    private byte[] bytesCol;

    /**
     * Data bytes array.
     */
    private Short shortCol;

    /**
     * Inner types column.
     */
    private InnerTypes innerTypesCol;

    /**
     * Enum column.
     */
    private enumType enumCol;

    /**
     * Map column.
     */
    private ConcurrentMap<String, String> mapCol = new ConcurrentHashMap<>();

    /**
     * Hash set column.
     */
    private HashSet<String> hashSetCol = new HashSet<>();

    /**
     * Enum type.
     */
    private enum enumType {
        ENUMTRUE, ENUMFALSE
    }

    /**
     * @param key Key.
     */
    public AllTypes(Long key) {
        init(key, Long.toString(key));
    }

    /**
     * @param key Key.
     * @param str String.
     */
    public AllTypes(Long key, String str) {
        init(key, str);
    }

    /**
     * @param key key
     * @param str string key
     */
    private void init(Long key, String str) {
        longCol = key;

        doubleCol = Math.round(1000 * Math.log10(longCol.doubleValue()));

        bigDecimalCol = BigDecimal.valueOf(doubleCol);

        doubleCol /= 100;

        strCol = str;

        if (key % 2 == 0) {
            booleanCol = true;

            enumCol = enumType.ENUMTRUE;

            innerTypesCol = new InnerTypes();
        } else {
            booleanCol = false;

            enumCol = enumType.ENUMFALSE;

            innerTypesCol = null;
        }

        intCol = key.intValue();

        bytesCol = strCol.getBytes();

        shortCol = (short) (((1000 * key) % 50000) - 25000);

        Long m = key % 7;
        for (Integer i = 0; i < m; i++)
            mapCol.put("map_key_" + (key + i), "map_value_" + (key + i));

        m = key % 11 / 2;
        for (Integer i = 0; i < m; i++)
            hashSetCol.add("hashset_" + (key + i));
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        List<String> hashSetColSortedList = new ArrayList<>(hashSetCol);

        Collections.sort(hashSetColSortedList);

        StringBuilder mapColStr = new StringBuilder("{");

        for (String key : new TreeSet<>(mapCol.keySet()))
            mapColStr.append(key).append("=").append(mapCol.get(key)).append(", ");

        if (mapColStr.length() > 1)
            mapColStr = new StringBuilder(mapColStr.substring(0, mapColStr.length() - 2));

        mapColStr.append("}");

        String innerTypesStr = "null";

        if (innerTypesCol != null)
            innerTypesStr = innerTypesCol.toString();

        return "AllTypes=[Long=" + longCol +
                ", double=" + doubleCol +
                ", String='" + strCol +
                "', boolean=" + booleanCol +
                ", int=" + intCol +
                ", bigDecimal=" + bigDecimalCol.toString() +
                ", bytes=" + Arrays.toString(bytesCol) +
                ", short=" + shortCol +
                ", InnerTypes=" + innerTypesStr +
                ", Map=" + mapColStr +
                ", HashSet=" + hashSetColSortedList.toString() +
                ", enum=" + enumCol.toString() +
                "]";
    }


    /**
     * Non-static inner types
     */
    private class InnerTypes implements Serializable {
        /**
         * Long column.
         */
        private Long longInnerCol;

        /**
         * String column.
         */
        private String strCol;

        /**
         * Array list column.
         */
        private ArrayList<Long> arrListCol = new ArrayList<>();

        /**
         * Default constructor.
         */
        InnerTypes() {
            longInnerCol = longCol;
            strCol = Long.toString(longCol);
            Long m = longCol % 8;
            for (Integer i = 0; i < m; i++)
                getArrListCol().add(longCol + i);
        }

        /** {@inheritDoc} */
        @Override
        public String toString() {
            return "[Long=" + longInnerCol +
                    ", String='" + strCol + "'" +
                    ", ArrayList=" + arrListCol.toString() +
                    "]";
        }

        /**
         * Long column.
         */
        Long getLongCol() {
            return longInnerCol;
        }

        /**
         * String column.
         */
        String getStrCol() {
            return strCol;
        }

        /**
         * Array list column.
         */
        ArrayList<Long> getArrListCol() {
            return arrListCol;
        }
    }

}
