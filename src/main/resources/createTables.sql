CREATE TABLE Author(
  id VARCHAR PRIMARY KEY
  ,name VARCHAR(20)
  ,dateOfBirth DATE
  ,dateOfDeath DATE
  ,gender VARCHAR(6)
);
CREATE TABLE Book(
  id VARCHAR PRIMARY KEY
  ,name VARCHAR(20)
  ,dateOfRelease DATE
);
CREATE TABLE Author_Book(
  authorId VARCHAR
  ,bookId VARCHAR
  ,PRIMARY KEY (authorId,bookId)
);
CREATE TABLE Publisher(
  id VARCHAR PRIMARY KEY
);
CREATE TABLE Book_Publisher(
  bookId VARCHAR
  ,publisherId VARCHAR
  ,PRIMARY KEY (bookId,publisherId)
);