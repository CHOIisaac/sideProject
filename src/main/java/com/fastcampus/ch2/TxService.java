package com.fastcampus.ch2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TxService {
    @Autowired A1Dao a1Dao;
    @Autowired B1Dao b1Dao;

    public void insertA1WithoutTx() throws Exception{
        a1Dao.insert(1, 100);
        a1Dao.insert(1, 200);

    }
    @Transactional(rollbackFor = Exception.class)
    public void insertA1WithoutTxFail() throws Exception{
        a1Dao.insert(1, 100);
        a1Dao.insert(1, 200);

    }
    @Transactional
    public void insertA1WithoutTxSuccess() throws Exception{
        a1Dao.insert(1, 100);
        a1Dao.insert(2, 200);

    }

}
