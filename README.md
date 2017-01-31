# recommendationengine
A stocastic recommendation engine, working on sales data, for datasets where the number of products is large (>2000) and the number of transactions is also large (>100000) and the number of distinct customers is large



Consider the following simple method of recommendations : 

I have a list of purchases. Each purchase is recorded as :
product ID,customer ID

I want to generate a list of recommendations such that for each product p I have a set of products that were also frequently purchased by customers who purchased p. Products that occur frequently in the dataset in general should not be included in this set of recommendations. 

For example, a customer A bought a Louis Vuitton handbag, a Louis Vuitton Scarf and a toilet roll . Recommending toilet rolls to Louis Vuitton customers is pointless as most customers bought toilet rolls at some point or another in the sample. Only 0.1% of customers bought a Louis Vuitton scarf, so this is a more relevant recommendation.

More formally, for a product p, we find S(p) the set of all customers who bought p. We then find P(S(p)) the set of all products bought by all these customers. For each product x in the set P(S(p))-p we score it as follows:
score(x,p) = f(x, P(S(p))) - f(x, all transactions)
where f(x,P(S(p))) is the frequency of x in the overall set of all products purchased by people who also purchased p and
f(x, all transactions) is the frequency of x in all transactions. 
We then report the highest scoring x's as being good recommendations for p.

In the toilet roll example, purchase of toilet rolls occur equally frequently in the whole population as they do in the population of Louis Vuitton handbag purchases. That derives :

score(toilet roll, Louis Vuitton handbag) = 0
whereas :
score(Louis Vuitton Scarf, Louis Vuitton handbag) > 0 

since the scarf purchase is much more common in the set P(S(Louis Vuitton Scarf)) than it is in the overall dataset.


This algorithm is simple and works very well when we have a limited number of products. If we have n products then, effectively we need to work out a sparse matrix of scores for, at worst case, all n^2 combinations of products, then evaluate which elements in each row of this matrix we should report as recommendations (i.e. sort each row of the matrix by score). It has a worst case complexity which is of O(n^2) therefore which is impractical for large values of n.

I therefore aim to present an algorithm which approximates the above and I want to do it in no worse than O(t log t) time where t is the number of transactions and O(n) memory complexity where n is the number of products.


A different approach is to try and identify customers who are like the current customer in terms of their purchases. Again an easy approach but if the number of customers is large and the number of purchases per customer is large then you end up with computational difficulties too. Spacially indexing the customer space based upon previous purchases is possible, but will end up with space complexity which too high to be practical. Approximate nearest neighbour methods could be used here. Find your approximate K nearest neighbours and generate recommendations out of the set of products purchased by them. 

I believe this to be a actually a similar idea expressed differently, I'm looking at recommendations based upon a purchase rather than a user, to extend to those for a user, you use the recommendations per product for each product purchased by that user.


