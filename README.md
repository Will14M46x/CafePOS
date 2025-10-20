WEEK5LAB: Exposing the Factory based construction is the obvious choice due to its simplicity. It allows developers to build complex drinks with only a simple recipe String without needing to change code. This makes the system easier to extend with base drinks, whereas Manual chaining would require constant changes.

WEEK6LAB: 
Q1: Removed Primitive Obsession, Feature Envy, Duplicated Logic and Shotgun Surgery Risk smells when refactoring the original discountCode logic, tax logic and payment logic.
Q2: applied "refactor(discount)" because the original discount logic was too primative and feature envy so we changed it to include a DiscountFactory which gives the exact same output as before but in a much more Object Oriented design.
Q3: Each class handles only one concern. New discounts or tax types can be added by implementing DiscountPolicy or TaxPolicy. And policies expose small, consistent interfaces. 
Q4: Simply just create a new class and implement DiscountPolicy.