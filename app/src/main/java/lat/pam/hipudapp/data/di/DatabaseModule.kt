package lat.pam.hipudapp.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import lat.pam.hipudapp.core.di.ApplicationScope
import lat.pam.hipudapp.data.local.db.HipudDatabase
import lat.pam.hipudapp.data.local.db.dao.AddressDao
import lat.pam.hipudapp.data.local.db.dao.DeliveryBatchDao
import lat.pam.hipudapp.data.local.db.dao.OrderDao
import lat.pam.hipudapp.data.local.db.dao.ProductDao
import lat.pam.hipudapp.data.local.db.dao.UserDao
import lat.pam.hipudapp.data.local.db.seedIfEmpty
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
        @ApplicationScope applicationScope: CoroutineScope,
    ): HipudDatabase {
        val database = Room.databaseBuilder(context, HipudDatabase::class.java, "hipud.db").build()
        applicationScope.launch { database.seedIfEmpty() }
        return database
    }

    @Provides
    fun provideUserDao(database: HipudDatabase): UserDao = database.userDao()

    @Provides
    fun provideProductDao(database: HipudDatabase): ProductDao = database.productDao()

    @Provides
    fun provideDeliveryBatchDao(database: HipudDatabase): DeliveryBatchDao = database.deliveryBatchDao()

    @Provides
    fun provideAddressDao(database: HipudDatabase): AddressDao = database.addressDao()

    @Provides
    fun provideOrderDao(database: HipudDatabase): OrderDao = database.orderDao()
}
