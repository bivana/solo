/*
 * Solo - A small and beautiful blogging system written in Java.
 * Copyright (c) 2010-present, b3log.org
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package org.b3log.solo.util;

import com.sun.crypto.provider.SunJCE;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.Security;

public class DesUtils {
    private static String strDefaultKey = "fsdfsdf";
    private Cipher encryptCipher;
    private Cipher decryptCipher;

    public static String byteArr2HexStr(byte[] arrB) throws Exception {
        int iLen = arrB.length;
        StringBuffer sb = new StringBuffer(iLen * 2);

        for(int i = 0; i < iLen; ++i) {
            int intTmp;
            for(intTmp = arrB[i]; intTmp < 0; intTmp += 256) {
            }

            if (intTmp < 16) {
                sb.append("0");
            }

            sb.append(Integer.toString(intTmp, 16));
        }

        return sb.toString();
    }

    public static byte[] hexStr2ByteArr(String strIn) throws Exception {
        byte[] arrB = strIn.getBytes();
        int iLen = arrB.length;
        byte[] arrOut = new byte[iLen / 2];

        for(int i = 0; i < iLen; i += 2) {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte)Integer.parseInt(strTmp, 16);
        }

        return arrOut;
    }

    public DesUtils() throws Exception {
        this(strDefaultKey);
    }

    public DesUtils(String strKey) throws Exception {
        this.encryptCipher = null;
        this.decryptCipher = null;
        Security.addProvider(new SunJCE());
        Key key = this.getKey(strKey.getBytes());
        this.encryptCipher = Cipher.getInstance("DES");
        this.encryptCipher.init(1, key);
        this.decryptCipher = Cipher.getInstance("DES");
        this.decryptCipher.init(2, key);
    }

    public byte[] encrypt(byte[] arrB) throws Exception {
        return this.encryptCipher.doFinal(arrB);
    }

    public String encrypt(String strIn) throws Exception {
        return byteArr2HexStr(this.encrypt(strIn.getBytes()));
    }

    public byte[] decrypt(byte[] arrB) throws Exception {
        return this.decryptCipher.doFinal(arrB);
    }

    public String decrypt(String strIn) throws Exception {
        return new String(this.decrypt(hexStr2ByteArr(strIn)));
    }

    private Key getKey(byte[] arrBTmp) throws Exception {
        byte[] arrB = new byte[8];

        for(int i = 0; i < arrBTmp.length && i < arrB.length; ++i) {
            arrB[i] = arrBTmp[i];
        }

        Key key = new SecretKeySpec(arrB, "DES");
        return key;
    }

    public static void main(String[] args) {
        try {
            String test = "jackeychow2003";
            DesUtils des = new DesUtils();
            System.out.println("加密前的字符：" + test);
            System.out.println("加密后的字符：" + des.encrypt(test));
            System.out.println("解密后的字符：" + des.decrypt(des.encrypt(test)));
            System.out.println(des.encrypt("solobdl@"));
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }
}
