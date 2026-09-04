package lat.pam.hipudapp.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import lat.pam.hipudapp.data.repository.AddressRepositoryImpl
import lat.pam.hipudapp.data.repository.AuthRepositoryImpl
import lat.pam.hipudapp.data.repository.CartRepositoryImpl
import lat.pam.hipudapp.data.repository.DeliveryBatchRepositoryImpl
import lat.pam.hipudapp.data.repository.OrderRepositoryImpl
import lat.pam.hipudapp.data.repository.ProductRepositoryImpl
import lat.pam.hipudapp.domain.repository.AddressRepository
import lat.pam.hipudapp.domain.repository.AuthRepository
import lat.pam.hipudapp.domain.repository.CartRepository
import lat.pam.hipudapp.domain.repository.DeliveryBatchRepository
import lat.pam.hipudapp.domain.repository.OrderRepository
import lat.pam.hipudapp.domain.repository.ProductRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindProductRepository(impl: ProductRepositoryImpl): ProductRepository

    @Binds
    @Singleton
    abstract fun bindCartRepository(impl: CartRepositoryImpl): CartRepository

    @Binds
    @Singleton
    abstract fun bindDeliveryBatchRepository(impl: DeliveryBatchRepositoryImpl): DeliveryBatchRepository

    @Binds
    @Singleton
    abstract fun bindAddressRepository(impl: AddressRepositoryImpl): AddressRepository

    @Binds
    @Singleton
    abstract fun bindOrderRepository(impl: OrderRepositoryImpl): OrderRepository
}
