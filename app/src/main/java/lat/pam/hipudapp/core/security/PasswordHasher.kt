package lat.pam.hipudapp.core.security

import java.security.MessageDigest
import java.security.SecureRandom

/** Salted SHA-256 hashing so credentials are never stored in plaintext. */
object PasswordHasher {

    fun generateSalt(): String {
        val bytes = ByteArray(16)
        SecureRandom().nextBytes(bytes)
        return bytes.joinToString("") { "%02x".format(it) }
    }

    fun hash(password: String, salt: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        digest.update(salt.toByteArray())
        val hashBytes = digest.digest(password.toByteArray())
        return hashBytes.joinToString("") { "%02x".format(it) }
    }

    fun matches(password: String, salt: String, expectedHash: String): Boolean =
        hash(password, salt) == expectedHash
}
