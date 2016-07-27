package com.algaworks.brewer.thymeleaf.processor;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IAttribute;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractElementTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

public class OrderElementTagProcessor extends AbstractElementTagProcessor {

	private static final String TAG_NAME = "order";
	private static final int PRECEDENCIA = 1000;

	public OrderElementTagProcessor(String dialectPrefix) {
		super(TemplateMode.HTML, dialectPrefix, TAG_NAME, true, null, false, PRECEDENCIA);
	}

	@Override
	protected void doProcess(ITemplateContext context, IProcessableElementTag tag,
			IElementTagStructureHandler structureHandler) {
		IModelFactory modelFactory = context.getModelFactory();
		
		IAttribute pagina = tag.getAttribute("pagina");
		IAttribute propriedade = tag.getAttribute("propriedade");
		IAttribute texto = tag.getAttribute("texto");
		
		
		IModel model = modelFactory.createModel();
		model.add(modelFactory.createStandaloneElementTag("th:block",
				"th:replace",
				String.format("fragments/Ordenacao :: order(%s,%s,%s)", pagina.getValue(), propriedade.getValue(), texto.getValue())));
		
		structureHandler.replaceWith(model, true);
	}

}
