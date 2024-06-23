App.class: App.java User.class Child.class Adult.class Admin.class Book.class Library.class LibraryCard.class Authenticate.class FileHandler.class

User.class: User.java Child.class Adult.class Admin.class Book.class Library.class LibraryCard.class Authenticate.class FileHandler.class

Child.class: Child.java Adult.class Admin.class Book.class Library.class LibraryCard.class Authenticate.class FileHandler.class

Adult.class: Adult.java Admin.class Book.class Library.class LibraryCard.class Authenticate.class FileHandler.class

Admin.class: Admin.java Book.class Library.class LibraryCard.class Authenticate.class FileHandler.class

Book.class: Book.java Library.class LibraryCard.class Authenticate.class FileHandler.class

Library.class: Library.java LibraryCard.class Authenticate.class FileHandler.class

LibraryCard.class: LibraryCard.java Authenticate.class FileHandler.class

Authenticate.class: Authenticate.java FileHandler.class

FileHandler.class: FileHandler.java

.PHONY: run jar clean

run: App.class
	java App

jar: App.class User.class Child.class Adult.class Admin.class Book.class Library.class LibraryCard.class Authenticate.class FileHandler.class

clean:
	rm -f *.class myapp.jar
