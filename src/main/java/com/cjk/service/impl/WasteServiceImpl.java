package com.cjk.service.impl;

import com.cjk.entity.Waste;
import com.cjk.service.WasteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class WasteServiceImpl extends IServiceImpl<Waste> implements WasteService {


}
