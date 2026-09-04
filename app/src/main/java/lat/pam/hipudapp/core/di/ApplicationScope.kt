package lat.pam.hipudapp.core.di

import javax.inject.Qualifier

/** CoroutineScope tied to the process lifetime, used for work that must outlive a single screen (e.g. DB seeding). */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApplicationScope
