CFLAGS=-g 

all: send recv 

send: send.o lib.o
	gcc $(CFLAGS) send.o lib.o -o send

recv: recv.o lib.o
	gcc $(CFLAGS) -g recv.o lib.o -o recv

.c.o: 
	gcc $(CFLAGS) -Wall -c $? 

clean:
	-rm send.o recv.o send recv 
