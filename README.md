# cechallenge

The main function can be found in the class SendEMailsApplication.

I created two interfaces. One called Storage to abstract storage concept. One called EMailSender to abstract email sending process. To transfer emails inside of the program I created EMail class. I prepared file localstoragefile.csv to do some test runs.

Storage implements Iterable interface to provide sequential access to emails in the storage.
I have implemented this Storage interface in the class CSVFileStorage, that provides access to a local CSV file that stores emails.
To make CSVFileStorage class work I implemented Iterator interface in the class CSVFileEMailIterator.
It uses standard Java IO classes to access file.

I implemented EMailSender interface in the class ConsoleEMailSender.
It's trivial, because I only had to print emails out.

I wrote two test classes, one for ConsoleEMailSender class and one for CSVFileEMailIteratorTest. The first one is again trivial, because implementation itself is trivial. The second one has quite some testing.
