package com.huayu.common.tool.code;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/** 
 *  
 * @author 梁栋 
 * @version 1.0 
 * @since 1.0 
 */  
public class RSACoderTest {  
    private String publicKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCv40cy+h8Ggim2ojgXjYga4S8kbzabkOlcRBE0rlf4GYKxADiFoY8GDCYgn3JSna9LvqiivEtmZm7kBwTmdfv+wRC5cG3Dt45Q2fn7ns5GHaqBRoPDYz/lSEUJnITiwPma4q5tc9p41EklsJNPKf74+H3WuDgVEFceyMV4sG3u+QIDAQAB";
    private String privateKey="MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAK/jRzL6HwaCKbaiOBeNiBrhLyRvNpuQ6VxEETSuV/gZgrEAOIWhjwYMJiCfclKdr0u+qKK8S2ZmbuQHBOZ1+/7BELlwbcO3jlDZ+fuezkYdqoFGg8NjP+VIRQmchOLA+Zrirm1z2njUSSWwk08p/vj4fda4OBUQVx7IxXiwbe75AgMBAAECgYEAjRLZZ5zQR7Mc6/yeyt1dHlohKrL89Le9RKDfwAZaTod/mKJZs20Kv0n9Rss8seEB2dn5flVSkLZ2GPU8S/C0BZ3y5kFchttZpzHum7c8JsGptIAJ7a55YuNxmhVgvdT7EOqdfz4Q2bKmPMmoZZDBezfB7WK+3RIQ2UErh/bWCNECQQD/PdLf8gIC93LQmAdODAJcGz6VtHtpOdlqOUAU5RSl6kYK5doUOwtCzK/icmC1RBwkJdUW3vHEg+0U7xjX6FtlAkEAsGkV7ig7esIwi0fQJAMwVvRUhYWInwd59opibbdA/7+fOzXvkppoc8OOue+hNuhrEPPEnRXpPAbNOyGqmxguBQJAMvgGhx/NlQgSzmK9erFdO0VZfw2WY1Hg/5xFFqcUM3mP2RdDMC0GN6WmLcMHWssqD3HaloGYp9RsYHTKcjr9HQJBAKRSXoEmrLDebcVvMSw6ZC67DHgLOIWZuzuxD5pPVpZjcfj/dpdCiQU8JZiA49R3jjOOPZhtLoIhe84WwAoT7dkCQQCw5ciF0r1Vi+ofxFqKYaXaATC1Z1XvMrxbo1j0tEJkQIiFtdVTEqaSkq6XLUP16vaB1a3B89ZyfLavldoT0sTM";
  
//    @Before
    public void setUp() throws Exception {  
        Map<String, Object> keyMap = RSACoder.initKey();
  
        publicKey = RSACoder.getPublicKey(keyMap);  
        privateKey = RSACoder.getPrivateKey(keyMap);  
        System.err.println("公钥: \n\r" + publicKey);
        System.err.println("私钥： \n\r" + privateKey);
    }

    @Test
    public void testEncode() throws Exception {
        System.err.println("公钥加密——私钥解密");
        String inputStr = "admin";
        byte[] data = inputStr.getBytes();

        byte[] encodedData = RSACoder.encryptByPublicKey(data, publicKey);
        String result = Coder.encryptBASE64(encodedData);
        System.out.println(result);
        byte[] bytes = Coder.decryptBASE64(result);

        byte[] decodedData = RSACoder.decryptByPrivateKey(bytes,
                privateKey);

        String outputStr = new String(decodedData);
        System.err.println("加密前: " + inputStr + "\n\r" + "解密后: " + outputStr);
    }



    @Test  
    public void test() throws Exception {  
        System.err.println("公钥加密——私钥解密");  
        String inputStr = "abc";  
        byte[] data = inputStr.getBytes();

        byte[] encodedData = RSACoder.encryptByPublicKey(data, publicKey);
        String result = Coder.encryptBASE64(encodedData);
        System.out.println(result);
        byte[] bytes = Coder.decryptBASE64(result);

        byte[] decodedData = RSACoder.decryptByPrivateKey(bytes,
                privateKey);  
  
        String outputStr = new String(decodedData);  
        System.err.println("加密前: " + inputStr + "\n\r" + "解密后: " + outputStr);
    }

    @Test
    public void testSign2() throws Exception {
        System.err.println("私钥加密——公钥解密");
        String inputStr = "sign";
        byte[] data = inputStr.getBytes();

        byte[] encodedData = RSACoder.encryptByPublicKey(data, publicKey);

        byte[] decodedData = RSACoder.decryptByPrivateKey(encodedData, privateKey);

        System.err.println("私钥签名——公钥验证签名");
        // 产生签名
        String sign = RSACoder.sign(encodedData, privateKey);
        System.err.println("签名:" + sign);

        // 验证签名
        boolean status = RSACoder.verify(encodedData, publicKey, sign);
        System.err.println("状态:" + status);
        assertTrue(status);

        String outputStr = new String(decodedData);
        System.err.println("加密前: " + inputStr + "\n\r" + "解密后: " + outputStr);
        assertEquals(inputStr, outputStr);



    }

    @Test  
    public void testSign() throws Exception {  
        System.err.println("私钥加密——公钥解密");  
        String inputStr = "sign";  
        byte[] data = inputStr.getBytes();  
  
        byte[] encodedData = RSACoder.encryptByPrivateKey(data, privateKey);  
  
        byte[] decodedData = RSACoder.decryptByPublicKey(encodedData, publicKey);
  
        String outputStr = new String(decodedData);  
        System.err.println("加密前: " + inputStr + "\n\r" + "解密后: " + outputStr);  
        assertEquals(inputStr, outputStr);  
  
        System.err.println("私钥签名——公钥验证签名");
        // 产生签名  
        String sign = RSACoder.sign(encodedData, privateKey);  
        System.err.println("签名:" + sign);
  
        // 验证签名  
        boolean status = RSACoder.verify(encodedData, publicKey, sign);  
        System.err.println("状态:" + status);
        assertTrue(status);  
  
    }
  
}  