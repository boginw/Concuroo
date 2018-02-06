#include<stdio.h>
#define SOLUTION 0x474E4148
int main() {
    int ask[12] = {
        0x53455547,0x48542053,0x4F572045,0x200A4452, 
        0x5F5F200A,0x202F0A5F,0x00007C20,0x20554F59,
        0x004E4957,0x20554F59,0x45534F4C,0x00
    },
        segment[3] = {0x00203020,0x005c7C2f,0x005c202f};
    int res = 1600085855,
        wi = 0x0A5A2D41,
        w = 0;
    while(w < 4 && res != SOLUTION) {
        puts((char*)&(ask));
        for(int i=1;i<=3;i++) {
            fputc(0x7c,stdout);
            if(w >= i) {
                fputc(0x20,stdout);
                puts((char*)&(segment[i-1])); 
            } else {
                fputc(0xA,stdout);
            }
        }
        fputc(0xA,stdout);
        for(int i = 0; i < 4; i++){
            fputc(((char *)&res)[i], stdout);
        }
        fputc(0xA,stdout);
        char input = fgetc(stdin);
        fgetc(stdin);
        if(input < 0x41 || input > 0x5A) { 
            for(int i=0; i<4; i++){
                fputc(((char*)&wi)[i], stdout);
            }
            continue;
        }
        int hit = 0;
        for(int i=0;i<4;i++){  
            if(input == ((SOLUTION >> (i*8)) & 0xFF)) {
                hit = 1;
                res = (res & ~(0xFF << ((i)*8))) | (input << ((i)*8));
            }
        }
        if(!hit) {
            w++;
        }
    }
    res == SOLUTION ? puts((char*)&(ask[7])) : puts((char*)&(ask[9])); 
}
