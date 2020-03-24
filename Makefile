NETBEANS=~/.packages/netbeans-*/
ANT=$(NETBEANS)extide/ant/bin/ant

run:
	$(ANT) run

compile:
	$(ANT) compile

.PHONY: compile run
