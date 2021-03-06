//tag::include[]
package org.hibernate.validator.referenceguide.chapter11.dynamicpayload;

//end::include[]

import java.util.Map;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

import static org.hibernate.validator.internal.util.CollectionHelper.newHashMap;

//tag::include[]
public class ValidPassengerCountValidator implements ConstraintValidator<ValidPassengerCount, Car> {

	private static final Map<Integer, String> suggestedCars = newHashMap();

	static {
		suggestedCars.put( 2, "Chevrolet Corvette" );
		suggestedCars.put( 3, "Toyota Volta" );
		suggestedCars.put( 4, "Maserati GranCabrio" );
		suggestedCars.put( 5, " Mercedes-Benz E-Class" );
	}

	@Override
	public void initialize(ValidPassengerCount constraintAnnotation) {
	}

	@Override
	public boolean isValid(Car car, ConstraintValidatorContext context) {
		if ( car == null ) {
			return true;
		}

		int passangerCount = car.getPassengers().size();
		if ( car.getSeatCount() >= passangerCount ) {
			return true;
		}
		else {

			if ( suggestedCars.containsKey( passangerCount ) ) {
				HibernateConstraintValidatorContext hibernateContext = context.unwrap(
						HibernateConstraintValidatorContext.class
				);
				hibernateContext.withDynamicPayload( suggestedCars.get( passangerCount ) );
			}
			return false;
		}
	}
}
//end::include[]


