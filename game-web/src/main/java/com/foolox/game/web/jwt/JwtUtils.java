package com.foolox.game.web.jwt;

import com.auth0.jwt.JWT;
import com.foolox.base.constant.result.CodeMessage;
import com.foolox.base.db.domain.Player;
import com.foolox.base.constant.result.Result;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Decoder;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 12/05/2019
 */
@Slf4j
public class JwtUtils {

    /**
     * 签发JWT
     *
     * @param player 登录成功的 clientSessions 对象，可以是JSON数据 尽可能少
     * @return String
     */
    public static String createJWT(Player player) {
        if (null == player) {
            return null;
        }
        //返回JWT字符串
        return builder(player).compact();
    }

    /**
     * 签发JWT
     *
     * @param player    登录成功的 clientSessions 对象，可以是JSON数据 尽可能少
     * @param ttlMillis 超时时间
     * @return String
     */
    public static String createJWT(Player player, long ttlMillis) {
        if (null == player) {
            return null;
        }
        JwtBuilder jwtBuilder = JwtUtils.builder(player);
        if (ttlMillis >= 0) {
            long expMillis = System.currentTimeMillis() + ttlMillis;
            Date expDate = new Date(expMillis);
            jwtBuilder.setExpiration(expDate);// 过期时间
        }
        //返回JWT字符串
        return jwtBuilder.compact();
    }

    /**
     * 设置jwt的body
     *
     * @param player
     * @return
     */
    private static JwtBuilder builder(Player player) {
        //指定签名的时候使用的签名算法，也就是header那部分，jjwt已经将这部分内容封装好了。
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        //生成JWT的时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证方式的）
        Map<String, Object> claims = new HashMap<String, Object>();
        claims.put("id", player.getId());
        claims.put("username", player.getUsername());
        claims.put("password", player.getPassword());
        //STAY放入其他参数

        SecretKey key = generalKey(player.getPassword());

        //为payload添加各种标准声明和私有声明
        return Jwts.builder()
                //如果有私有声明，一定要先设置这个自己创建的私有声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setClaims(claims)
                //设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
                .setId(UUID.randomUUID().toString())
                //代表这个JWT的主题，即它的所有人，这个是一个json格式的字符串，可以存放用户id，roldid之类的，作为什么用户的唯一标志。
//                .setSubject(player.getId())
                // 签发者
                .setIssuer("foolox.domain")
                // 签发时间
                .setIssuedAt(now)
                // 签名算法以及密匙,密匙使用用户的密码
                .signWith(signatureAlgorithm, key);
    }

    /**
     * Token的解密
     *
     * @param token 加密后的token
     * @param key   签名密钥
     * @return
     */
    public static Result<Claims> parseJWT(String token, SecretKey key) {
        //得到DefaultJwtParser
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    //设置签名时使用的秘钥
                    .setSigningKey(key)
                    //设置需要解析的jwt
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return Result.fail(CodeMessage.LOGIN_TOKEN_EXPIRED);
        } catch (SignatureException e) {
            return Result.fail(CodeMessage.LOGIN_TOKEN_INCORRECT);
        } catch (Exception e) {
            return Result.fail(CodeMessage.SERVER_ERROR);
        }
        return Result.success(claims);
    }

    /**
     * 校验token
     * 在这里可以使用官方的校验，我这里校验的是token中携带的密码于数据库一致的话就校验通过
     *
     * @param token
     * @param player
     * @return
     */
    public static Boolean isVerify(String token, Player player) {

        Result<Claims> claimsResult = parseJWT(token, generalKey(player.getPassword()));
        if (claimsResult.getCode() == 0) {
            return true;
        }
        return false;
    }

    /**
     * 生成签名的时候使用的秘钥secret,这个方法本地封装了的，一般可以从本地配置文件中读取，切记这个秘钥不能外露。
     * 它就是你服务端的私钥，在任何场景都不应该流露出去。一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了。
     *
     * @param keyword
     */
    private static SecretKey generalKey(String keyword) {
        byte[] encodedKey = new byte[0];
        try {
            encodedKey = new BASE64Decoder().decodeBuffer(keyword);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("JwtUtils generalKey fail!");
        }
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    public static void main(String[] args) {
        Player player = new Player();
        player.setId(111l);
        player.setUsername("222");
        player.setPassword("333");
        String jwt = JwtUtils.createJWT(player);
        System.out.println(jwt);

        String s = JWT.decode(jwt).getClaim("id").asString();
        System.out.println("id =" + s);

        Result<Claims> claimsResult = parseJWT(jwt, generalKey(player.getPassword()));
        System.out.println(claimsResult);

        Boolean verify = isVerify(jwt, player);
        System.out.println(verify);

    }
}
