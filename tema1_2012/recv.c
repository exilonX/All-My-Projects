#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include "lib.h"

#define HOST "127.0.0.1"
#define PORT 10001

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
int power(int p ,int q){
  int i;
  int rez=1;
  for(i=0;i<q;i++)
    rez *= p;
  return rez;
}

int gcrt(int n){
    int q = len(n);
    int k = 0;
    int s =0;
    while(n !=0 && k<q-1){
      s += (n%10)*power(10,k);
      k++;
      n /=10;
    }
  return s;
}
int main(int argc,char** argv){
  msg *r,t;
  init(HOST,PORT);
    FILE *f = fopen("recvout","wt");
  char filename[1400], filesize[1400];
  int fs;

  r = receive_message();
  
  if (!r){
    perror("Receive message");
    return -1;
  }

  //find the filename;
  int name = 1, crt = 0,i;

  if (r->type!=1){
    printf("Expecting filename and size message\n");
    return -1;
  }

  for (i=0;i<r->len;i++){
    if (crt>=1400){
      printf("Malformed message received! Bailing out");
      return -1;
    }

    if (name){
      if (r->payload[i]=='\n'){
	name = 0;
	filename[crt] = 0;
	crt = 0;
      }
      else 
	filename[crt++] = r->payload[i];
    }
    else {
      if (r->payload[i]=='\n'){
	name = 0;
	filesize[crt] = 0;
	crt = 0;
	break;
      }
      else 
	filesize[crt++] = r->payload[i];
    }
  }
  fs = atoi(filesize);
  char fn[2000];
  
    int ok=0;
  char nrp[10000];
  int k=0;
  int nrpls = 0;
  for(i=0;i<strlen(filename);i++){
    if(ok==1){
      nrp[k]=filename[i];
      k++;
      
    }else{
      if((filename[i] == '+')&&ok==0){
	nrpls = i;
	ok =1;
      }
    }
  }
  filename[nrpls] = '\0';
  int numberpack = atoi(nrp);
 
  
  sprintf(fn,"recv_%s", filename);
  printf("Receiving file %s of size %d\n",fn,fs);

  int fd = open(fn,O_WRONLY|O_CREAT,0644);
  if (fd<0) {
    perror("Failed to open file\n");
  }

    k=0;
  int lastnr =-1;
  msg coada[numberpack] ;
  int countack = 0;
  int notack=0;
  int empty = 0;
  while (fs>0){
    
    printf("Left to read %d\n",fs);
    r = receive_message();
    if (!r){
      perror("Receive message");
      return -1;
    }    
    
    if ((int)r->type/(power(10,len(r->type)-1))!=2){
      printf("Expecting filename and size message\n");
      return -1;
    }
    fprintf(f, "gcrt :%i\n ",-1*(numberpack-1));
   if( (gcrt(r->type)) - lastnr ==1 || ((gcrt(r->type)) - lastnr == -1*(numberpack-1))){
      if(notack ==0){
	write(fd, r->payload, r->len);
	fs -= r->len;
	t.type = 3;
	char intst[32];
	sprintf(intst, "%d", gcrt(r->type));
	char ack[5] = "ACK";
	strcat(ack, intst); 
	sprintf(t.payload,ack);
	//fprintf(f,"payload %s\n",t.payload);
	
	t.len = strlen(t.payload)+1;
	send_message(&t);
	lastnr = gcrt(r->type);
	free(r);
      }else
      {
	/*
	 coada[gcrt(r->type)] = *r;
	 // count ack numara toate ack urile  if(getcrt(x) < nowack)care au fost primite dupa ce 
	 // s-a inregistrat o pierdere de pachete, daca este egal cu numarul
	 // total de pachete adica coada este plina, atunci trimitem toate 
	 // pachetele din coada
	 if(countack == numberpack){
	   for(i=0;i<numberpack;i++){
	     write(fd, coada[i].payload, coada[i].len);
	     fs -= coada[i].len;
	     t.type = 3;
	    char intst[32];
	    sprintf(intst, "%d", gcrt(coada[i].type));
	    char ack[5] = "ACK";
	    strcat(ack, intst); 
	    sprintf(t.payload,ack);
	    //fprintf(f,"payload %s\n",t.payload);
	    
	    t.len = strlen(t.payload)+1;
	    send_message(&t);
	     
	  }
	   lastnr = 0;
	    notack = 0;
	   
	}
	*/
      }
    }
    
  }
  
    fclose(f);
  return 0;
}
