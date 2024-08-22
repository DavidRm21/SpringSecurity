package com.cris.products.token;


import org.springframework.stereotype.Service;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Service
public class ES256KeyService {

    private static final String ALGORITHM = "EC"; // Elliptic Curve
    private static final String CURVE = "secp256r1"; // P-256 curve (alias secp256r1)

    private KeyPair keyPair;

    // Generar un nuevo par de llaves (privada y pública)
    public void generateKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
        keyPairGenerator.initialize(new ECGenParameterSpec(CURVE));
        this.keyPair = keyPairGenerator.generateKeyPair();
    }

    // Obtener la llave pública en formato Base64
    public String getPublicKey() {
        PublicKey publicKey = this.keyPair.getPublic();
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }

    // Obtener la llave privada en formato Base64
    public String getPrivateKey() {
        PrivateKey privateKey = this.keyPair.getPrivate();
        return Base64.getEncoder().encodeToString(privateKey.getEncoded());
    }

    // Guardar una llave pública desde un string en Base64
    public void setPublicKey(String base64PublicKey) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(base64PublicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        this.keyPair = new KeyPair(publicKey, this.keyPair.getPrivate());
    }

    // Guardar una llave privada desde un string en Base64
    public void setPrivateKey(String base64PrivateKey) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(base64PrivateKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        this.keyPair = new KeyPair(this.keyPair.getPublic(), privateKey);
    }

    // Obtener el par de llaves
    public KeyPair getKeyPair() {
        return this.keyPair;
    }

    public static void main(String[] args) throws Exception {
        ES256KeyService es256KeyService = new ES256KeyService();
        es256KeyService.generateKeyPair();
        System.out.println("Lsito");
        System.out.println("Llave publica ".concat(es256KeyService.getPublicKey()));
        System.out.println("Llave privada ".concat(es256KeyService.getPrivateKey()));
        es256KeyService.setPrivateKey(es256KeyService.getPrivateKey());
        es256KeyService.setPublicKey(es256KeyService.getPublicKey());
        System.out.println("Llave privada ".concat(es256KeyService.getPrivateKey()));
        System.out.println("Llave publica ".concat(es256KeyService.getPublicKey()));
    }
}

