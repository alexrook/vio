/* entangling names in document table */

update document set name='Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus' where id%2=0;

update document set name='Curabitur ullamcorper ultricies nisi. Nam eget dui. Etiam rhoncus. Maecenas tempus, tellus eget condimentum rhoncus, sem quam semper libero, sit amet adipiscing sem neque sed ipsum' where id%3=0;

update document set name='One morning, when Gregor Samsa woke from troubled dreams, he found himself transformed in his bed into a horrible vermin. He lay on his armour-like back, and if he lifted his head a little he' where id%5=0;

update document set name='A wonderful serenity has taken possession of my entire soul, like these sweet mornings of spring which I enjoy with my whole heart' 
where not (id%2=0 or id%3=0 or id%5=0);