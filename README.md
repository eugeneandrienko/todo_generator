# todo_generator
Small program to generate LaTeX files with marked (or dated) lists from pure text files.

## How to use this program

First, you should prepare plain text file(s) with next format:
* Each row should be filled with someone todo/date/note item or filled with comment (started from #).
* Use `-` symbol for simple TODO entry, for example: `- Buy applejuice`. Result will be like: `☐ Buy applejuice`.
* Use `[DD.MM.YYYY]` text for entry with date, for example: `[20.12.2018] Pay for domain`. Result will be like: `◯ [20.12.2018] Pay for domain`.
* Use `[DD.MM.YYYY HH:mm]` for entry with date and time, for example: `[20.12.2018 12:33] Call to the Mom`.  Result will be the next: `◯ [20.12.2018 12:33] Call to the Mom`.
* Use `[DD.MM.YYYY-DD.MM.YYYY]` for entry with date period. Second date should be bigger than first date. For example: `[20.12.2018-21.12.2018] Holidays`. Result will be like: `◯ [20.12.2018-21.12.2018] Holidays`.
* Use `~` symbol for simple note entry, for example: `~ http://example.org`. Result will be the next: `— http://example.org`.

After that, call this generator with the next command:
```bash
java -cp todo-generator-1.1.jar com.eugene_andrienko.Main input_file.txt output_file.tex
```
In the output TEX file will be simple LaTeX commands which generates our nice TODO-list with `latex` or
`pdflatex`.

## Setup LaTeX

I include generated tex-files in my main LaTeX file, where in preamble I placed next commands to draw
checkboxes for __itemize__ elements by default:
```latex
\renewcommand{\labelitemi}{$\square$}
\renewcommand{\labelitemii}{$\square$}
\renewcommand{\labelitemiii}{$\square$}
\renewcommand{\labelitemiv}{$\square$}
```

Other elements, like datetime or note elements, generates with standard LaTeX features, without any tricks:
```latex
\item[\textbigcircle] [20.12.2018 12:33] Call to the Mom
\item[--] http://example.org
```
