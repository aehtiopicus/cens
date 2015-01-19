/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.midasconsultores.catalogodefiltros.configuration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HardwareInformation {

	@Autowired
	private DataBaseStrategy dataBaseEstrategy;
	
	private static String fingerPrint;

	private HardwareInformation() {
	}
	
	public static String generarCodigoMaquina(){
		return generateSHA2();
	}

	private static String generateSHA2() {

		try {
			String password = value();

			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(password.getBytes());

			byte byteData[] = md.digest();

			// convert the byte to hex format method 2
			StringBuffer hexString = new StringBuffer();

			for (int i = 0; i < byteData.length; i++) {
				String hex = Integer.toHexString(0xff & byteData[i]);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}

	}

	public boolean validarSerial() {

		String valueDB = getValueFromDB();
		if (!isNullOrEmpty(valueDB)) {
			String value = generateSHA2();
			return valueDB.equals(value);
		}
		return false;
	}

	private String getValueFromDB() {
		return dataBaseEstrategy.getValueFromDB();
	}

	private static String value() {
		if (fingerPrint == null || fingerPrint.isEmpty()) {
			fingerPrint = getHash("1 >> " + cpuId() + "\n2 >> " + biosId()
					+ "\n3 >> " + baseId());
		}
		return fingerPrint;
	}

	private static String getHash(String plaintext) {
		try {
			
			MessageDigest m = MessageDigest.getInstance("MD5");
			m.reset();
			m.update(plaintext.getBytes());
			byte[] digest = m.digest();
			BigInteger bigInt = new BigInteger(1, digest);
			String hashtext = bigInt.toString(16);

			// Now we need to zero pad it if you actually want the full 32
			// chars.
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}

			return hashtext;

		} catch (NoSuchAlgorithmException ex) {
			return null;
		}
	}

	private static String identifier(String wmiClass, String wmiProperty) {
		String result = "";
		try {
			File file = File.createTempFile("script", ".vbs");
			file.deleteOnExit();
			FileWriter fw = new java.io.FileWriter(file);

			String vbs = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n"
					+ "Set colItems = objWMIService.ExecQuery _ \n"
					+ "   (\"Select * from "
					+ wmiClass
					+ "\") \n"
					+ "For Each objItem in colItems \n"
					+ "    Wscript.Echo objItem."
					+ wmiProperty
					+ " \n"
					+ "    exit for  ' do the first cpu only! \n" + "Next \n";

			fw.write(vbs);
			fw.close();
			Process p = Runtime.getRuntime().exec(
					"cscript //NoLogo " + file.getPath());
			BufferedReader input = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			String line;
			while ((line = input.readLine()) != null) {
				result += line;
			}
			input.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.trim().equals("null") ? null : result.trim();
	}

	private static String cpuId() {
		// Uses first CPU identifier available in order of preference
		// Don't get all identifiers, as it is very time consuming
		String retVal = identifier("Win32_Processor", "UniqueId");

		if (isNullOrEmpty(retVal)) {// If no UniqueID, use ProcessorID

			retVal = identifier("Win32_Processor", "ProcessorId");

			if (isNullOrEmpty(retVal)) // If no ProcessorId, use Name
			{
				retVal = identifier("Win32_Processor", "Name");

				if (isNullOrEmpty(retVal)) // If no Name, use Manufacturer
				{
					retVal = identifier("Win32_Processor", "Manufacturer");
				}
				// Add clock speed for extra security
				retVal += identifier("Win32_Processor", "MaxClockSpeed");
			}
		}
		return retVal;
	}

	private static boolean isNullOrEmpty(String value) {
		return value == null || value.isEmpty();
	}

	private static String biosId() {
		return identifier("Win32_BIOS", "Manufacturer")
				+ identifier("Win32_BIOS", "SMBIOSBIOSVersion")
				+ identifier("Win32_BIOS", "IdentificationCode")
				+ identifier("Win32_BIOS", "SerialNumber")
				+ identifier("Win32_BIOS", "ReleaseDate")
				+ identifier("Win32_BIOS", "Version");
	}

	private static String baseId() {
		return identifier("Win32_BaseBoard", "Model")
				+ identifier("Win32_BaseBoard", "Manufacturer")
				+ identifier("Win32_BaseBoard", "Name")
				+ identifier("Win32_BaseBoard", "SerialNumber");
	}

}
