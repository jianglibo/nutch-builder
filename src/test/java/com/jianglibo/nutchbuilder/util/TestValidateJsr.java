package com.jianglibo.nutchbuilder.util;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

import org.hibernate.validator.constraints.ScriptAssert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestValidateJsr {

	private static Validator validator;
	
	@BeforeClass
	public static void b() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	    validator = factory.getValidator();
	}
	
	@Test
	public void t() {
		Be be = new Be("1234456666", 3); // a is not valid.
		Set<ConstraintViolation<Be>> cvs = validator.validate(be);
		assertThat("no violation", cvs.size(), equalTo(0)); // if a constrain has a group, it only validate when group is applied.
		
		cvs = validator.validate(be, CreateGroup.class);
		assertThat("one violation", cvs.size(), equalTo(1));
	}
	
	@Test
	public void tScript() {
		Scv be = new Scv("1234456666", 4); // a is not valid.
		Set<ConstraintViolation<Scv>> cvs = validator.validate(be);
		assertThat("no violation", cvs.size(), equalTo(0)); // if a constrain has a group, it only validate when group is applied.
	}
	
	
	public static interface CreateGroup {}
	public static interface ModifyGroup {}
	
	
	public static class Be {
		
		@Size(min=3, max=6, groups={CreateGroup.class})
		private String a;
		
		@Min(value=1,  groups={ModifyGroup.class, Default.class})
		@Max(value = 10, groups={ModifyGroup.class})
		private int b;

		public Be(String a, int b) {
			super();
			this.a = a;
			this.b = b;
		}

		public String getA() {
			return a;
		}

		public void setA(String a) {
			this.a = a;
		}

		public int getB() {
			return b;
		}

		public void setB(int b) {
			this.b = b;
		}
	}
	
	@ScriptAssert(lang="javascript", alias="_", script="_.b > 3 ? true : false")
	public static class Scv {
		
		private String a;
		
		@Min(value=1,  groups={ModifyGroup.class, Default.class})
		@Max(value = 10, groups={ModifyGroup.class})
		private int b;

		public Scv(String a, int b) {
			super();
			this.a = a;
			this.b = b;
		}

		public String getA() {
			return a;
		}

		public void setA(String a) {
			this.a = a;
		}

		public int getB() {
			return b;
		}

		public void setB(int b) {
			this.b = b;
		}
	}
}
