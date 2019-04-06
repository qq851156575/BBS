package com.piu.software.service;

import com.piu.base.CrudService;
import com.piu.software.dao.FileInterfaceDao;
import com.piu.software.entity.FileInterface;
import org.springframework.stereotype.Service;

@Service
public class FileInterfaceService extends CrudService<FileInterfaceDao, FileInterface> {
}
