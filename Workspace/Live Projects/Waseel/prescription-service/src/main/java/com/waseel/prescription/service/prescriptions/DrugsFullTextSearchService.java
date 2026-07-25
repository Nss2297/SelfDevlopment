package com.waseel.prescription.service.prescriptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.mapper.orm.Search;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.waseel.prescription.model.common.DrugServiceModel;
import com.waseel.prescription.persist.mdss.DrugService;

@Service
@Profile("elasticsearch")
public class DrugsFullTextSearchService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DrugsFullTextSearchService.class);

	@PersistenceContext(unitName = "mdss")
	private EntityManager entityManager;

	@Transactional
	public Page<DrugServiceModel> search(String value, Long formularyId, Long activeDrugListId, String searchBy,
			int pageNumber, int recordSize) {
		LOGGER.info(
				"Drug Full Text Service: Search query: Value: {}, Formulary Id: {}, Active DrugListId: {}, SearchBy: {}",
				value, formularyId, activeDrugListId, searchBy);
		SearchResult<DrugService> result = Search.session(entityManager).search(DrugService.class)
				.where(f -> f.and().with(and -> {
					and.add(f.match().field("drugListId").matching(activeDrugListId));
					if (searchBy.equals("tradeName")) {
						and.add(f.match().fields("otherCodesValue", "display", "ingredients", "scientificCode",
								"strengthUnit", "dosageForm", "strength", "roaSuggested").matching(value));
					} else {
						and.add(f.match()
								.fields("ingredients", "scientificCode", "strengthUnit", "dosageForm", "strength", "roaSuggested")
								.matching(value));
					}
				})).sort(f -> f.score().desc()).fetch(searchBy.equals("tradeName") ? recordSize : (recordSize * 2));
		LOGGER.info("Drug Full Text Service: Search Result Hits: {}", result.hits().size());
		PageRequest pageRequest = PageRequest.of(pageNumber, recordSize);
		List<DrugService> hits = result.hits();
		final List<DrugService> finalHits = new ArrayList<>();
		if (!searchBy.equals("tradeName") && !hits.isEmpty()) {
			finalHits.addAll(
					hits.stream().filter(distinctByKey(DrugService::getScientificCode)).collect(Collectors.toList()));
		} else {
			finalHits.addAll(hits);
		}

		return PageableExecutionUtils.getPage(
				finalHits.stream().map(hit -> new DrugServiceModel(hit.getPrice(), hit.getOtherCodesValue(),
						hit.getDisplay(), hit.getIngredients(), hit.getScientificCode(), hit.getDosageForm(),
						hit.getStrengthUnit(), hit.getWaseelDrugId(), hit.getLastUpdatedDate(),
						hit.getDrugFormularyDetailsList().stream()
								.filter(formulary -> formulary.getFormularyId().equals(formularyId)
										&& !formulary.getIsDeleted())
								.findAny().isPresent() ? formularyId : null,
						false, hit.getStrength(), hit.getRoaSuggested(), hit.getDrugListId()))
						.collect(Collectors.toList()),
				pageRequest, () -> finalHits.size());
	}

	public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {

		Map<Object, Boolean> seen = new ConcurrentHashMap<>();
		return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}

}
