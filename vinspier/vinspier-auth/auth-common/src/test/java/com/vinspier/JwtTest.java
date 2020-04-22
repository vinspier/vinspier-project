package com.vinspier;

import com.vinspier.auth.pojo.UserInfo;
import com.vinspier.auth.utils.JwtUtils;
import com.vinspier.auth.utils.RsaUtils;
import org.junit.Before;
import org.junit.Test;

import java.security.PrivateKey;
import java.security.PublicKey;

public class JwtTest {
    private static final String pubKeyPath = "E:\\vinspier-project\\vinspier\\vinspier-auth\\auth-common\\src\\main\\resources\\rsa.pub";

    private static final String priKeyPath = "E:\\vinspier-project\\vinspier\\vinspier-auth\\auth-common\\src\\main\\resources\\rsa.pri";

    private PublicKey publicKey;

    private PrivateKey privateKey;

    @Test
    public void testRsa() throws Exception {
        RsaUtils.generateKey(pubKeyPath, priKeyPath, "23SDF23SD344");
    }

    @Before
    public void testGetRsa() throws Exception {
        this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
        this.privateKey = RsaUtils.getPrivateKey(priKeyPath);
    }

    @Test
    public void testGenerateToken() throws Exception {
        // 生成token
        String token = JwtUtils.generateToken(new UserInfo(20L, "jack"), privateKey, 5);
        System.out.println("token = " + token);
    }

    @Test
    public void testParseToken() throws Exception {
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJpZCI6MjAsInVzZXJuYW1lIjoiamFjayIsImV4cCI6MTU4NzU2MzY3M30.jreqdbXTNyBiIDR9ReNrwjPZ-pTqvfgqCJh8xRbGXPovhELjOK6rtwTufyeXIKeEkazO_X12MHfF49J5RtBjILw3Sfcb0Ed9b4lesLXtgn_wJyBbdAUTPCyETCdTxvJeNH4YLy1Md7to7E67jmkCGxBMJcxULV3_WOOO7B44Rew";

        // 解析token
        UserInfo user = JwtUtils.getInfoFromToken(token, publicKey);
        System.out.println("id: " + user.getId());
        System.out.println("userName: " + user.getUsername());
    }
}
