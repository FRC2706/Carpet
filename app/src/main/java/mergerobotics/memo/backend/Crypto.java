package mergerobotics.memo.backend;

import android.util.Base64;
import android.util.Log;

import com.goterl.lazycode.lazysodium.LazySodiumAndroid;
import com.goterl.lazycode.lazysodium.SodiumAndroid;
import com.goterl.lazycode.lazysodium.interfaces.Helpers;
import com.goterl.lazycode.lazysodium.interfaces.KeyDerivation;
import com.goterl.lazycode.lazysodium.interfaces.Sign;
import com.goterl.lazycode.lazysodium.utils.Key;
import com.goterl.lazycode.lazysodium.utils.KeyPair;

import org.json.JSONObject;

public class Crypto {
	
	private static Key privatekey;
	private static Key publickey;
	private static LazySodiumAndroid sodium;
	private static Helpers.Lazy helpers;
	
	public static void init(String b64_seed)
	{
		sodium = new LazySodiumAndroid(new SodiumAndroid());
		helpers = (Helpers.Lazy) sodium;
		try {
			KeyPair generated = sodium.cryptoSignSeedKeypair(Base64.decode(b64_seed, Base64.URL_SAFE));
			privatekey = generated.getSecretKey();
			publickey = generated.getPublicKey();
		} catch (Exception e) {
		
		}
	}
	
	public static String getSignature(String str)
	{
		try {
			return Base64.encodeToString(helpers.sodiumHex2Bin(sodium.cryptoSignDetached(str, privatekey)), Base64.URL_SAFE).replace("\n", "");
		} catch (Exception e) {
			return null;
		}
	}
	
	public static String getSignatureRow(JSONObject obj)
	{
		return getSignature(obj.toString());
	}
	
	public static boolean verifySignatureRow(JSONObject obj, String publickey)
	{
		try {
			String signature = obj.getString("sn");
			obj.remove("sn");
			return verifySignature(obj.toString(), publickey, signature);
		} catch (Exception e) {
			return false;
		}
	}
	
	public static boolean verifySignature(String str, String publickey, String signature)
	{
		try {
			byte[] keybytes = Base64.decode(publickey, Base64.URL_SAFE);
			byte[] sigbytes = Base64.decode(signature, Base64.URL_SAFE);
			byte[] strbytes = str.getBytes();
			return sodium.cryptoSignVerifyDetached(sigbytes, strbytes, strbytes.length, keybytes);
		} catch (Exception e){
			return false;
		}
	}
	
	public static String getPublicKey()
	{
		return Base64.encodeToString(publickey.getAsBytes(), Base64.URL_SAFE).replace("\n", "");
	}

}
