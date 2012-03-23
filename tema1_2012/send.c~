#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/types.h>
#include <sys/stat.h>
#include "lib.h"

#define HOST "127.0.0.1"
#define PORT 10000
int power(int p ,int q){
  int i;
  int rez=1;
  for(i=0;i<q;i++)
    rez *= p;
  return rez;
}
int getcrt(char* p){
    
    char * q = p+3;
    
    //printf("%i",strlen(q));
    int i=0;
    int rez=0;
    for(i=strlen(q)-1;i>=0;i--){
      char c = *(q+i);
     
      rez += (c-'0')*power(10,strlen(q)-1-i);
      
    }
  return rez;
}

int len(int k)
{
  if(k ==0)
    return 1;
  int p =0;
  while(k!=0){
    p++;
    k/=10;
  }
  return p;
}

void send_file(char* filename,int numberpack){
  msg t;
  msg coada[numberpack];
  struct stat buf;
  if (stat(filename,&buf)<0){
    perror("Stat failed");
    return;
  }

  int fd = open(filename, O_RDONLY);

  if (fd<0){
    perror("Couldn't open file");
    return;
  }
  FILE *f = fopen("sendout","wt");
  
  t.type = 1;
  char  rez[10000];
  sprintf(rez,"%d",numberpack);
  strcat(filename,"++");
  strcat(filename,rez);
 // fprintf(f,"%s",filename);
  sprintf(t.payload,"%s\n%d\n",filename,(int)buf.st_size);
  
  t.len = strlen(t.payload)+1;
  
  send_message(&t);
  

  t.type = 2;
  int cont = 0;
  int last=0;
    while(cont < numberpack){
	if((t.len = read(fd,&t.payload, 1400))>0){
	  t.type = 2;
	  t.type *= power(10,len(cont));
	  t.type += cont;
	  
	 // fprintf(f,"\n %i\n",t.type);
	  coada[cont] = t;
	  send_message(&coada[cont]);
	  
	  fprintf(f,"%i\n\n%s\n",coada[cont].type,coada[cont].payload);
	  
	  //fprintf(f,"aicisea");
	  //fprintf(f,"%s",coada[cont].payload);
	  last = cont;
	  cont++;
	}
	else {
	  break;
	}
    }
    
    printf("terminat");
  int i =0;
  
  printf("aici este %i",cont);
  int lastack = -1;
  int allp=cont-1;
   int allack[numberpack];
 
  int ok =0;
  
  for(i=0;i<numberpack;i++)
    allack[i]=0;
  int finish = 1;
  // ack ul pe care ar trebui sa l primeasca
  int nowack = 0;
  int where = 0;
  int ackrecv[numberpack];
  int notack = 0;
  for(i=0;i<numberpack;i++)
    ackrecv[i] = 0;
  while (1){
  
    //wait for ack, max 10 ms, then cycle - assume no packet loss
    msg* r = NULL;
    while (!r){
      r = receive_message_timeout(10);
      
     
      if (!r){
	//there was an error on receive!
	perror("receive error");
	printf("there was an error on receive");
	
	
      } else {
      
      char * x = r->payload;
      char c = *x;
      if(c == 'A'){
	  if(getcrt(x) >= nowack){
	    if(getcrt(x) == nowack ){
		
		if(nowack == numberpack -1)
		  nowack = 0;
		else nowack++;
		
		ackrecv[getcrt(x)] = 1;
		
		if(notack==0)
		  where = nowack;
		
		if((t.len = read(fd,&t.payload, 1400))>0){
		  //fprintf(f,"%s\n\n\n",t.type);
		  t.type = 2;
		  t.type *= power(10,len(getcrt(x)));
		  t.type += getcrt(x);
		  coada[getcrt(x)] = t;
		  send_message(&coada[getcrt(x)]);
		  ackrecv[getcrt(x)] = 0;
		  //fprintf(f,"%i\n\n%s\n",coada[getcrt(x)].type,coada[getcrt(x)].payload);
		  // fprintf(f,"%s\n\n","aicisea");
		  }
		  else 
		  { 
		    finish = 0; 
		    break;
		  }
	      }
	      else{
		/*
		// am pierdut unul sau mai multe pachete 
		notack = 0;
		// marcam pachetul ca primit 
		ackrecv[getcrt(x)] = 1;
		// retrimitem tot pana la el 
		for(i=nowack;i<getcrt(x);i++)
		  send_message(&coada[i]);
		
		*/
		
		
	      }
	  } else {
		//retrimit tot de la nowack la getcrt(x)
		
		
		
	      }
	      
      }
      else{
	if(c == 'N'){
	  send_message(&coada[getcrt(r->payload)]);
	  lastack = getcrt(r->payload);
	  
	  
	}
	
      }
      }
     free(r); 
    }
    
    if(finish == 0){
      break;
    }
    //wait for ack, blocking
    //msg* r = receive_message();
    
  }
fclose(f);
  close(fd);
}

int main(int argc,char** argv){
  init(HOST,PORT);
	
  if (argc<2){
    printf("Usage %s filename\n",argv[0]);
    return -1;
  }
  
  double numberpack = ((atoi (argv[1] + 6)) * (atoi (argv[2] + 6))*1024) / 1408.0;
  int np = (int)numberpack;
  printf("number of packets %i", np); 
  send_file(argv[5],np);
  return 0;
}
