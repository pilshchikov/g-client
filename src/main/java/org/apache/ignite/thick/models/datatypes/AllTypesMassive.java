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
 * Why did the lion get lost in the jungle
 * <p>
 * Created by gridgain on 23.03.2016.
 */
public class AllTypesMassive extends Timestamped {
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
     * Inner types column.
     */
    private InnerStaticTypes innerStaticTypesCol;

    /**
     * String array.
     */
    private ArrayList<String> strArr;

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
     * ENUM
     */
    private enum enumType {
        ENUMTRUE, ENUMFALSE
    }

    /**
     * @param key Key.
     */
    public AllTypesMassive(Long key) {
        init(key, Long.toString(key));
    }

    /**
     * @param key Key.
     * @param str String.
     */
    public AllTypesMassive(Long key, String str) {
        init(key, str);
    }

    /**
     * @param key key
     * @param str string value
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
            innerStaticTypesCol = new InnerStaticTypes(key);
            innerTypesCol = this.new InnerTypes();
        } else {
            booleanCol = false;
            enumCol = enumType.ENUMFALSE;
            innerTypesCol = null;
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

        strArr = new ArrayList<>();
        String toStr = toString();
        for (int i = 0; i < 1000; ++i)
            strArr.add(toStr + i);
    }

    /**
     * @return getter for massive String array
     */
    public List<String> getStrArr() {
        return Collections.unmodifiableList(strArr);
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

        if (innerStaticTypesCol != null)
            innerTypesStr = innerStaticTypesCol.toString();

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
     * Static inner type
     */
    private static class InnerStaticTypes implements Serializable {
        /**
         * Long column.
         */
        private Long longCol;

        /**
         * String column.
         */
        private String strCol;

        /**
         * Array list column.
         */
        private ArrayList<Long> arrListCol = new ArrayList<>();

        /**
         * @param key Key.
         */
        InnerStaticTypes(Long key) {
            longCol = key;

            strCol = Long.toString(key);

            Long m = key % 8;

            for (Integer i = 0; i < m; i++)
                arrListCol.add(key + i);
        }

        /** {@inheritDoc} */
        @Override
        public String toString() {
            return "[Long=" + longCol +
                    ", String='" + strCol + "'" +
                    ", ArrayList=" + arrListCol.toString() +
                    "]";
        }
    }

    /**
     * Non-static inner type
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
                arrListCol.add(longCol + i);
        }

        /** {@inheritDoc} */
        @Override
        public String toString() {
            return "[Long=" + longInnerCol +
                    ", String='" + strCol + "'" +
                    ", ArrayList=" + arrListCol.toString() +
                    "]";
        }
    }
}
