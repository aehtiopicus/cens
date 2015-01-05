package com.aehtiopicus.cens.service.cens;


import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;




@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=PersistenceJPAConfigDev.class)
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class UsuarioCensServiceImplTest {

}
