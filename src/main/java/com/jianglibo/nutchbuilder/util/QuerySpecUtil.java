package com.jianglibo.nutchbuilder.util;

import java.util.Optional;
import java.util.stream.Collectors;

import io.katharsis.queryspec.Direction;
import io.katharsis.queryspec.FilterOperator;
import io.katharsis.queryspec.FilterSpec;
import io.katharsis.queryspec.QuerySpec;

/**
 * If only there is a way to translate QuerySpec to the final execution code.
 * By now, take the manually parse solution.  
 *  
 * @author jianglibo@hotmail.com
 *
 */

public class QuerySpecUtil {

	public static Optional<Long> hasId(QuerySpec spec) {
		Optional<FilterSpec> fs =  spec.getFilters().stream().filter(f -> f.getOperator() == FilterOperator.EQ && f.getAttributePath().size() == 1 && "id".equals(f.getAttributePath().get(0))).findAny();
		if (fs.isPresent()) {
			Long id = (Long) fs.get().getValue();
			return Optional.of(id);
		} else {
			return Optional.empty();
		}
	}
	
	public static String[] getSortFields(QuerySpec spec) {
		return spec.getSort().stream().map(sort -> {
			String f = sort.getAttributePath().stream().collect(Collectors.joining(","));
			return sort.getDirection() == Direction.ASC ? f : "-" + f;
		}).toArray(f -> new String[]{});
	}
	
	public static Optional<String> getFilterStringValue(QuerySpec querySpec, String fn) {
		Optional<FilterSpec> ofs = querySpec.getFilters().stream().filter(f -> fn.equals(f.getAttributePath().get(0))).findAny();
		if (ofs.isPresent()) {
			Object v = ofs.get().getValue();
			if (v == null) {
				return Optional.empty();
			}
			if (v instanceof String) {
				if (((String) v).trim().isEmpty()) {
					return Optional.empty();
				} else {
					return Optional.of(((String) v).trim());
				}
			} else {
				throw new RuntimeException(String.format("filter type is not implementation.", v.getClass().getName()));
			}
		} else {
			return Optional.empty();
		}
	}
}
