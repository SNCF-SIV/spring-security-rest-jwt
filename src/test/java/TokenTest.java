
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;
import com.sncf.siv.poc.security.JwtUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.text.ParseException;


@RunWith(BlockJUnit4ClassRunner.class)
public class TokenTest {

    public final static String SECRET = "ksfdlkvopiurutueijflkdsvf,cjnjnxjnvsoifoiropiezaropioezkvf,k,c kv,ckvdkfjgvorieoigfopziefpozepfiezikfozkfldsmvflkvdldvl,fdvk,fdkv,dkfgkjfdgkjvicooiviuzieopiztpoirpotimldkflg,vlkfckjshayauiueruergregierpogipdfogiklxvcjnxvjnfvjdsmkfmslfklgkfgoirgjitrooritjg";
    public final static String SECRET2 = "ksfdlkvopiurutueijflkdsvf,cjnjnxjnvsoifoiropiezaropioezkvf,k,c kv,ckvdkfjgvorieoigfopziefpozepfiezikfozkfldsmvflkvdldvl,fdvk,fdkv,dkfgkjfdgkjvicooiviuzieopiztpoirpotimldkflg,vlkfckjshayauiueruergregierpogipdfogiklxvcjnxvjnfvjdsmkfmslfklgkfgoirgjitrooritj1";

    public final static int EXPIRATION = 1;

    @Test
    public void generateTokenAndCheck() throws JOSEException, ParseException {

        String token = JwtUtils.generateHMACToken("Florent", "ROLE_1", SECRET, EXPIRATION);
        SignedJWT signedJWT = JwtUtils.parse(token);

        boolean verified = JwtUtils.verifyHMACToken(signedJWT, SECRET);

        Assert.assertTrue(verified);
        Assert.assertEquals("Florent", JwtUtils.getUsername(signedJWT));
    }

    @Test
    public void generateTokenAndCheckWithDifferentKey() throws JOSEException, ParseException {

        String token = JwtUtils.generateHMACToken("Florent", "ROLE_1", SECRET, EXPIRATION);
        SignedJWT signedJWT = JwtUtils.parse(token);
        boolean verified = JwtUtils.verifyHMACToken(signedJWT, SECRET2);
        Assert.assertFalse(verified);
    }


    @Test
    public void generateTokenAndGetContentWithoutChecking() throws JOSEException, ParseException {

        String token = JwtUtils.generateHMACToken("Florent", "ROLE_1", SECRET, EXPIRATION);
        SignedJWT signedJWT = JwtUtils.parse(token);
        Assert.assertEquals("Florent", JwtUtils.getUsername(signedJWT));
    }



}
