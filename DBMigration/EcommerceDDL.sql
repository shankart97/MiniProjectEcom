
create database Miniproject;
use Miniproject;
create table Product(product_id int not null auto_increment primary key , description varchar(250), price int ,name varchar(200),quantity int );
insert into Product(product_id, description, price,name ,quantity)values(1,"4K,Full-HD,55inch dolby-atmos",55000, "Samsung-TV",20);
insert into Product(product_id, description, price,name ,quantity)values(2,"5 Star,power saving double door",35000, "Samsung-Fridge",10);
insert into Product(product_id, description, price,name ,quantity)values(3,"5 Star,Fully Automatic",45000, "Washing Machine",25);
insert into Product(product_id, description, price,name ,quantity)values(4,"Dell ,i5 ,windows 11,RAM-8GB",52000, "Laptop",30);
insert into Product(product_id, description, price,name ,quantity)values(5,"LG ,3-Star",15000, "Oven",15);
insert into Product(product_id, description, price,name ,quantity)values(6,"LG ,2-tonne, 3-Star",40000, "AC",10);
insert into Product(product_id, description, price,name ,quantity)values(7,"5 Seater",60000, "Sofa",12);

insert into Product(product_id, description, price,name ,quantity)values(8,"JBL,LED display,Wireless",20000, "Speaker",18);
insert into Product(product_id, description, price,name ,quantity)values(9,"Commercial,5-Star",57000, " Cooler",24);
insert into Product(product_id, description, price,name ,quantity)values(10,"3-Liter",15000 ,"Geyser",35);


create table Customer_Details(cust_id int not null auto_increment primary key,userName varchar(255),password varchar(255) );

create table Order_Details(order_id int not null auto_increment primary key, cust_id int, when_created datetime default current_timestamp);

create table order_Products(order_id int ,product_id int,foreign key(order_id) references Order_Details(order_id),foreign key(product_id) references Product(product_id));
