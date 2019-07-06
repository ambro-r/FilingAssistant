# Filing Assistant

## Overview

Neatly filing documents (i.e. invoices, statements, etc) received from service providers can become a tedious task. Filing assistant helps to take away some of the grunt work though organising files on your local machine based on a defined set of rules.
   
## Job File Specification

A typical job file will contain **sources**, **locations** and **documents**.

```xml
<?xml version="1.0"?>
<job>
    <sources>
        ...
    </sources>
    <locations>
        ...
    </locations>
    <documents>
        ...
    </documents>
</job>
```

### Sources

Defines all the source locations for the assistant to run through in order to find documents to file. In some cases one may also want to rescursively also traverse through the sub-directory tree as well for documents but adding the attribute *recursive="true"*.

```xml
    <sources>
        <source directory="C:\Documents\Single Source" />
        <source directory="C:\Documents" recursive="true"/>
    </sources>
```

### Locations

Defines identifiers for target filing locations. These locations really on server avoid having to write out the entire target directory location when defining documents to file. 

```xml
    <locations>
        <location identifier="IDENTIFIER01" directory="C:\location\01" />
        <location identifier="IDENTIFIER02" directory="C:\location\02" />
    </locations>
```

### Documents

Defines all the documents the file assistant needs to file. 

```xml
    <documents>
        <document pattern="STATEMENT_.{10}_1234.pdf" target="$IDENTIFIER01$\statement" groupby="YEAR" action="move" contains="Account Number: 1234" />
        <document pattern="\d*_monthly.[pP][dD][fF]" target="$IDENTIFIER02$\Invoices" groupby="YEAR" dateprefix="yyyy-MM" action="copy" contains="SOME TEXT" />
        <document pattern="yearly_statement\d.pdf" target="c:\customer\directory" groupby="YEAR" action="move" contains="37790/279855/INVRA" dateprefix="yyyy" />
    </documents>
```

* **pattern**: The file pattern to look for. This could either be the file name, or a regular expression describing the file. For example, *STATEMENT_.{10}_1234.pdf* will match any file that begins with *STATEMENT_*, has any 10 characters and ends with *_1234.pdf* (this is useful when you receive generated statements who's naming pattern thends to change because of a generation date).

* **target**: Where the file needs to be stored. If a **location** was specified it needs to denoted by *$\<location\>$*. For example, *$IDENTIFIER01$\statement* will resolve to *C:\Documents\Single Source\statement*

* **groupby**: Specify if you want files grouped. This will create a sub-directory for that grouping. Presently only groupings by **year** is supported. 

* **action**: Specify what you want to do with the file. Only **move** or **copy** are supported.

* **contains**: If specified, it will check the file to see if it contains a specific string (only pdf is supported). This is useful when you get poorly named statements (i.e. *statement.pdf*). 

* **dateprefix**: Specify if you want a date prefix added to the file. The prefix pattern can be anything supported by SimpleDateFormat.

NOTE: All functions, which use date work on todays date (i.e. the date the application is run).

