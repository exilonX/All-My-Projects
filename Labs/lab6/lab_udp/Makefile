#
#  	Protocoale de comunicatii: 
#  	Laborator 6: UDP
#	mini-server de backup fisiere
# 	Makefile

CFLAGS = -Wall -g

# Portul pe care asculta serverul
PORT = 6666

# Adresa IP a serverului
IP_SERVER = 172.16.6.248

# Fisierul de unde citeste clientul
FILE_CLIENT1 = send.txt
FILE_CLIENT2 = send2.txt

# Fisierul unde salveaza serverul
FILE_SERVER = server_random

all: server client 

# Compileaza server.c
server: server.c

# Compileaza client.c
client: client.c

.PHONY: clean random_file run_server run_client

# Construieste un fisier de 10MB (1024B*10^5)==10MB
random_file:
	dd if=/dev/urandom of=${FILE_CLIENT} bs=1024 count=10000

# Ruleaza serverul
run_server:
	./server ${PORT} ${FILE_SERVER}

# Ruleaza clientul 	
run_client:
	./client ${IP_SERVER} ${PORT} ${FILE_CLIENT1} ${FILE_CLIENT2}

clean:
	rm -f server client ${FILE_CLIENT} ${FILE_SERVER}
