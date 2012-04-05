/*
*  	Protocoale de comunicatii: 
*  	Laborator 6: UDP
*	client mini-server de backup fisiere
*/

#include "helpers.h"

void usage(char*file)
{
	fprintf(stderr,"Usage: %s ip_server port_server file\n",file);
	exit(0);
}

/*
*	Utilizare: ./client ip_server port_server nume_fisier_trimis
*/
int main(int argc,char**argv)
{
	if (argc!=5)
		usage(argv[0]);
	
	int fd;
	struct sockaddr_in to_station;
	char buf[BUFLEN];


	/*Deschidere socket*/
	
	int s = socket(PF_INET,SOCK_DGRAM,0);
	
	DIE( s == -1, "open  socket failed");
	
		
	/* Deschidere fisier pentru citire */
	
	
	/*Setare struct sockaddr_in pentru a specifica unde trimit datele*/
	
	to_station.sin_family = AF_INET;
	to_station.sin_port = htons(6666);
	to_station.sin_addr.s_addr = inet_addr("172.16.6.248");
	
	/*struct sockadr adr;
	adr.sa_family = AF_INET;
	adr.sa_data = inet_ntoa(to_station.sin_addr.s_addr);
	*/
	
	//setare port
	
	//int b = bind(s,(struct sockaddr*) &to_station,sizeof(struct sockaddr));
	//DIE( b  == -1, "bind failed");
	/*
	*  cat_timp  mai_pot_citi
	*		citeste din fisier
	*		trimite pe socket
	*/	
	int i,len;
	for(i=0;i<argc-3;i++){
		DIE((fd=open(argv[3+i],O_RDONLY))==-1,"open file");
		printf("sending file %i",i);
		while((len = read(fd,buf,BUFLEN-1)) > 0) {
			buf[len]='\0';
			printf("%s\n",buf);
			sendto(s,buf,len+1,0,(struct sockaddr*) &to_station,sizeof(struct sockaddr));
		}
		/*Inchidere fisier*/
		close(fd);
	}
	/*Inchidere socket*/
	close(s);


	return 0;
}
