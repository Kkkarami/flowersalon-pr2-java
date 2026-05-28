package com.fedelesh.flowersalon.bootstrap;

import com.fedelesh.flowersalon.application.contract.AccessoryService;
import com.fedelesh.flowersalon.application.contract.AuthService;
import com.fedelesh.flowersalon.application.contract.BouquetService;
import com.fedelesh.flowersalon.application.contract.FlowerService;
import com.fedelesh.flowersalon.application.contract.OrderService;
import com.fedelesh.flowersalon.application.contract.SignUpService;
import com.fedelesh.flowersalon.application.contract.UserService;
import com.fedelesh.flowersalon.application.impl.AccessoryServiceImpl;
import com.fedelesh.flowersalon.application.impl.AuthServiceImpl;
import com.fedelesh.flowersalon.application.impl.BouquetServiceImpl;
import com.fedelesh.flowersalon.application.impl.FlowerServiceImpl;
import com.fedelesh.flowersalon.application.impl.OrderServiceImpl;
import com.fedelesh.flowersalon.application.impl.SignUpServiceImpl;
import com.fedelesh.flowersalon.application.impl.UserServiceImpl;
import com.fedelesh.flowersalon.infrastructure.email.EmailSender;
import com.fedelesh.flowersalon.infrastructure.email.SmtpEmailSender;
import com.fedelesh.flowersalon.infrastructure.persistence.contract.AccessoryRepository;
import com.fedelesh.flowersalon.infrastructure.persistence.contract.BouquetRepository;
import com.fedelesh.flowersalon.infrastructure.persistence.contract.FlowerRepository;
import com.fedelesh.flowersalon.infrastructure.persistence.contract.OrderItemRepository;
import com.fedelesh.flowersalon.infrastructure.persistence.contract.OrderRepository;
import com.fedelesh.flowersalon.infrastructure.persistence.contract.UserRepository;
import com.fedelesh.flowersalon.infrastructure.persistence.impl.AccessoryRepositoryImpl;
import com.fedelesh.flowersalon.infrastructure.persistence.impl.BouquetRepositoryImpl;
import com.fedelesh.flowersalon.infrastructure.persistence.impl.FlowerRepositoryImpl;
import com.fedelesh.flowersalon.infrastructure.persistence.impl.OrderItemRepositoryImpl;
import com.fedelesh.flowersalon.infrastructure.persistence.impl.OrderRepositoryImpl;
import com.fedelesh.flowersalon.infrastructure.persistence.impl.UserRepositoryImpl;
import com.fedelesh.flowersalon.infrastructure.security.BcryptPasswordHasher;
import com.fedelesh.flowersalon.infrastructure.security.PasswordHasher;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class AppModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(UserRepository.class).to(UserRepositoryImpl.class).in(Singleton.class);
    bind(FlowerRepository.class).to(FlowerRepositoryImpl.class).in(Singleton.class);
    bind(BouquetRepository.class).to(BouquetRepositoryImpl.class).in(Singleton.class);
    bind(AccessoryRepository.class).to(AccessoryRepositoryImpl.class).in(Singleton.class);
    bind(OrderRepository.class).to(OrderRepositoryImpl.class).in(Singleton.class);
    bind(OrderItemRepository.class).to(OrderItemRepositoryImpl.class).in(Singleton.class);

    bind(PasswordHasher.class).to(BcryptPasswordHasher.class).in(Singleton.class);
    bind(EmailSender.class).to(SmtpEmailSender.class).in(Singleton.class);

    bind(AuthService.class).to(AuthServiceImpl.class).in(Singleton.class);
    bind(SignUpService.class).to(SignUpServiceImpl.class).in(Singleton.class);
    bind(UserService.class).to(UserServiceImpl.class).in(Singleton.class);
    bind(FlowerService.class).to(FlowerServiceImpl.class).in(Singleton.class);
    bind(BouquetService.class).to(BouquetServiceImpl.class).in(Singleton.class);
    bind(AccessoryService.class).to(AccessoryServiceImpl.class).in(Singleton.class);
    bind(OrderService.class).to(OrderServiceImpl.class).in(Singleton.class);
  }
}
