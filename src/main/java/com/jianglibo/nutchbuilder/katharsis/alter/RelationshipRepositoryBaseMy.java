package com.jianglibo.nutchbuilder.katharsis.alter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.katharsis.core.internal.query.QuerySpecAdapter;
import io.katharsis.core.internal.repository.adapter.ResourceRepositoryAdapter;
import io.katharsis.core.internal.utils.MultivaluedMap;
import io.katharsis.core.internal.utils.PreconditionUtil;
import io.katharsis.core.internal.utils.PropertyUtils;
import io.katharsis.queryspec.FilterOperator;
import io.katharsis.queryspec.FilterSpec;
import io.katharsis.queryspec.QuerySpec;
import io.katharsis.repository.RelationshipRepositoryBase;
import io.katharsis.repository.response.JsonApiResponse;
import io.katharsis.resource.information.ResourceInformation;
import io.katharsis.resource.list.DefaultResourceList;
import io.katharsis.resource.list.ResourceList;
import io.katharsis.resource.registry.RegistryEntry;
import io.katharsis.resource.registry.ResourceRegistry;

/**
 * @see ResourceRepositoryAdapter , @see RepositoryLinksFilterChain , @see JsonApiUrlBuilder
 * 
 * @author jianglibo@hotmail.com
 *
 * @param <T>
 * @param <D>
 */
public class RelationshipRepositoryBaseMy<T, D> extends RelationshipRepositoryBase<T, Long, D, Long> {
	
	private ResourceRegistry resourceRegistry;

	protected RelationshipRepositoryBaseMy(Class<T> sourceResourceClass, Class<D> targetResourceClass) {
		super(sourceResourceClass, targetResourceClass);
	}

	@SuppressWarnings("unchecked")
	public MultivaluedMap<Long, D> findTargets(Iterable<Long> sourceIds, String fieldName, QuerySpec querySpec) {
		RegistryEntry sourceEntry = resourceRegistry.findEntry(getSourceResourceClass());
		ResourceInformation sourceInformation = sourceEntry.getResourceInformation();

		String oppositeName = getOppositeName(fieldName);
		QuerySpec idQuerySpec = querySpec.duplicate();
		idQuerySpec.addFilter(new FilterSpec(Arrays.asList(oppositeName, sourceInformation.getIdField().getJsonName()), FilterOperator.EQ, sourceIds));
		idQuerySpec.includeRelation(Arrays.asList(oppositeName));

		ResourceRepositoryAdapter<D, Long> targetAdapter = getTargetAdapter();
		JsonApiResponse response = targetAdapter.findAll(new QuerySpecAdapter(idQuerySpec, resourceRegistry));
		
		final List<D> results = (List<D>) response.getEntity();
		
		MultivaluedMap<Long, D> bulkResult = new MultivaluedMap<Long, D>() {
			@Override
			protected List<D> newList() {
				if (results instanceof ResourceList) {
					return new DefaultResourceList<>(((ResourceList<D>)results).getMeta(), ((ResourceList<D>)results).getLinks());
					
				} else {
					return new DefaultResourceList<>();
				}
				
			}
		};

		Set<Long> sourceIdSet = new HashSet<>();
		for (Long sourceId : sourceIds) {
			sourceIdSet.add(sourceId);
		}

		for (D result : results) {
			handleTarget(bulkResult, result, sourceIdSet, oppositeName, sourceInformation);
		}
		return bulkResult;
	}
	
	
	@SuppressWarnings("unchecked")
	private void handleTarget(MultivaluedMap<Long, D> bulkResult, D result, Set<Long> sourceIdSet, String oppositeName, ResourceInformation sourceInformation) {
		Object property = PropertyUtils.getProperty(result, oppositeName);
		if (property == null) {
			throw new IllegalStateException("field " + oppositeName + " is null for " + result + ", make sure to properly implement relationship inclusions");
		}
		if (property instanceof Iterable) {
			for (T potentialSource : (Iterable<T>) property) {
				Long sourceId = (Long) sourceInformation.getId(potentialSource);
				if (sourceId == null) {
					throw new IllegalStateException("id is null for " + potentialSource);
				}
				// for to-many relations we have to assigned the found resource
				// to all matching sources
				if (sourceIdSet.contains(sourceId)) {
					bulkResult.add(sourceId, result);
				}
			}
		} else {
			T source = (T) property;
			Long sourceId = (Long) sourceInformation.getId(source);
			PreconditionUtil.assertTrue("filtering not properly implemented in resource repository", sourceIdSet.contains(sourceId));
			if (sourceId == null) {
				throw new IllegalStateException("id is null for " + source);
			}
			bulkResult.add(sourceId, result);
		}
	}
	
	@Override
	public void setResourceRegistry(ResourceRegistry resourceRegistry) {
		this.resourceRegistry = resourceRegistry;
		super.setResourceRegistry(resourceRegistry);
	}

	
	
	private ResourceRepositoryAdapter<D, Long> getTargetAdapter() {
		RegistryEntry entry = resourceRegistry.findEntry(getTargetResourceClass());
		return entry.getResourceRepository(null);
	}

	private ResourceRepositoryAdapter<T, Long> getSourceAdapter() {
		RegistryEntry entry = resourceRegistry.findEntry(getSourceResourceClass());
		return entry.getResourceRepository(null);
	}

}
