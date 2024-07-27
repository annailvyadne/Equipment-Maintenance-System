package com.gabriel.mainms.transform;
import com.gabriel.mainms.entity.mainData;
import com.gabriel.mainms.model.main;
public interface TransformmainService {
	mainData transform(main main);
	main transform(mainData mainData);
}
