DROP TABLE IF EXISTS AUTHOR,BOOK,AUTHOR_BOOK,PUBLISHER,BOOK_PUBLISHER;
CREATE TABLE AUTHOR(
  id VARCHAR PRIMARY KEY
  ,name VARCHAR(20)
  ,dateOfBirth DATE
  ,dateOfDeath DATE
  ,gender VARCHAR(6)
);
CREATE TABLE BOOK(
  id VARCHAR PRIMARY KEY
  ,name VARCHAR(20)
  ,dateOfRelease DATE
);
CREATE TABLE AUTHOR_BOOK(
  authorId VARCHAR
  ,bookId VARCHAR
  ,PRIMARY KEY (authorId,bookId)
);
CREATE TABLE PUBLISHER(
  id VARCHAR PRIMARY KEY
);
CREATE TABLE BOOK_PUBLISHER(
  bookId VARCHAR
  ,publisherId VARCHAR
  ,PRIMARY KEY (bookId,publisherId)
);