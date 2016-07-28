package com.algaworks.brewer.thymeleaf;

import java.util.HashSet;
import java.util.Set;

import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;

import com.algaworks.brewer.thymeleaf.processor.ClassForErrorAtributeTagProcessor;
import com.algaworks.brewer.thymeleaf.processor.MessageElementTagProcessor;
import com.algaworks.brewer.thymeleaf.processor.OrderElementTagProcessor;
import com.algaworks.brewer.thymeleaf.processor.PaginationElementTagProcessor;

public class BrewerDialect extends AbstractProcessorDialect {

	private static final String DIALECT_PREFIX = "brewer";
	private static final String DIALECT_DESCRIPTION = "AlgaWorks Brewer";

	public BrewerDialect() {
		super(DIALECT_DESCRIPTION, DIALECT_PREFIX, StandardDialect.PROCESSOR_PRECEDENCE);
	}

	@Override
	public Set<IProcessor> getProcessors(String dialectPrefix) {
		final Set<IProcessor> processors = new HashSet<>();
		processors.add(new ClassForErrorAtributeTagProcessor(dialectPrefix));
		processors.add(new MessageElementTagProcessor(dialectPrefix));
		processors.add(new OrderElementTagProcessor(dialectPrefix));
		processors.add(new PaginationElementTagProcessor(dialectPrefix));
		return processors;
	}

}
