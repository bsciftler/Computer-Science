Andrew Quijano's Shell Script
CUNYID: 14193591

NOTICE:
The commands are CASE-SENSATIVE!
echo is a valid command.
EcHO is NOT a valid command.
etc.


NOTICE: This shell supports background processes
A background process is when a process is executed on the shell
and the process can run on its own without any user input.
To activate this I must append the command with an "&" key
Example:
ls&

clr - Clear screen
This command will clear out all previous commands/outputs in the console...
Example:
quan3951> ls
Project1a.java
CompileandRun
readme.txt
quan3591> clr

After command:
quan3591>

echo "comment" - display "comment" on the console followed by a new line.
Example:
quan3591> echo "Hello, are you studying for Operating Systems class?"
Hello, are you studying for Operating Systems class?
quan3591>

Note: The "" are important. If one or both commas are missing, the command will NOT work as intended.
The shell will inform you if you are missing one or both commas.

help - Display the user manual you are reading right now to the console.

pause - pause operation of the shell until the ENTER KEY is pressed. 
NOTE if you type "enter" it will NOT cancel pause!
Example:
quan3591> pause
rg
rgs
sr
rgsrg
srgs

quan3591>

exit - Quit the shell.

Final remark:
If you input a valid CentOS shell command, this shell will forward the script to 
the Operating System. Therefore, commands such as:
-ls
-cat
-pwd
are available to you.  This shell will also catch the error messages generated by the Operating System
and inform you the error.

Note on possible errors and how the shell will handle it:
1- If an invalid number of command line arguments are made in the shell
2- The file does not exit or cannot be opened
The shell will print a message about the error and close.

If a command that does not exist or can't be executed is typed into the shell,
the shell will inform you about the invalid command and wait for your new input.