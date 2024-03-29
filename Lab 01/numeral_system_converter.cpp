// numeral_system_converter.cpp: определяет точку входа для консольного приложения.
//



#include "stdafx.h"


int COUNTER = 0;        //number of IPs in file
int ip_numbers_array[4];        //chosen IP

int display_file(void);
int choose_ip(int);
int convert(int);
char returnChar(int);

int main(int argc, const char * argv[]) {

	int user_input = -1;
	int num_system;

	while (user_input != 0) {
		puts("\nInput number of IP which you want to convert or 0 for Exit:");
		display_file();
		printf("%s%d... ", "Input IP number - less or equal ", COUNTER);
		scanf("%d", &user_input);
		if (user_input == 0)
			continue;
		choose_ip(user_input);
		printf("%s", "Choose numeral system to convert: 2, 8 or 16 ?\nInput system and press Enter... ");
		scanf("%d", &num_system);
		convert(num_system);
	}


	return 0;
}

int display_file() {
	COUNTER = 0;
	char buf[512];
	char ch;
	int length = 0;
	FILE * file = fopen("ips.txt", "r");

	if (file != NULL)
	{
		while (fgets(buf, 512, file) != NULL)
		{
			COUNTER++;
			length = strlen(buf);
			for (int i = 0; i < length; i++)
			{
				ch = buf[i];
				if (ch == '\n')
				{
					buf[i] = '\0';
				}
			}

			printf("%d. %s\n", COUNTER, buf);
		}
		fclose(file);
	}
	else
	{
		puts("Can not open file ips.txt...");
		return 1;
	}

	return 0;
}

int choose_ip(int line) {
	FILE * file = fopen("ips.txt", "r");

	int count = 0;
	char buf[512];
	char ch;
	int length = 0;
	int single_number_array[3] = { -1, -1, -1 };     //0-255
	int number_of_char = 0;                             //for single_number_array
	int split_count = 0;                            //increment when find '.' , max is 3

	if (file != NULL)
	{
		while (count <= line)
		{
			count++;
			fgets(buf, 512, file);
			if (count != line)
				continue;
			length = strlen(buf);
			for (int i = 0; i < length - 1; i++)       //last char is '\n'
			{
				ch = buf[i];
				if (ch != '.')
				{
					single_number_array[number_of_char] = ch - '0';         //to convert char to int
					number_of_char++;

					//quick fix, not good but work
					if (i == length - 2)
					{
						number_of_char--;
						int pow = 1;
						int temp_number = 0;
						while (number_of_char >= 0)
						{
							temp_number += single_number_array[number_of_char] * pow;
							number_of_char--;
							pow *= 10;
						}
						ip_numbers_array[split_count] = temp_number;
						number_of_char = 0;
						split_count++;
					}

				}
				else
				{
					number_of_char--;
					int pow = 1;
					int temp_number = 0;
					while (number_of_char >= 0)
					{
						temp_number += single_number_array[number_of_char] * pow;
						number_of_char--;
						pow *= 10;
					}
					ip_numbers_array[split_count] = temp_number;
					number_of_char = 0;
					split_count++;
				}


			}

		}
	}
	else
	{
		puts("Can not open file ips.txt...");
		return 1;
	}
	return 0;
}

int convert(int system) {
	//int converted_arr[4];
	int splitter = 0;
	int temp;
	int ostatok;
	int temp_arr[8] = { 0 };
	int temp_arr_counter = 0;

	for (int i = 0; i < 4; i++)
	{
		for (int k = 0; k < 8; k++)
			temp_arr[k] = 0;
		temp_arr_counter = 0;

		int num_to_convert = ip_numbers_array[i];
		if (num_to_convert < system)
		{
			temp_arr[temp_arr_counter] = num_to_convert;
			//            temp_arr_counter++;
		}
		else
		{
			temp = num_to_convert / system;
			ostatok = num_to_convert % system;
			while (temp >= system)
			{
				temp_arr[temp_arr_counter] = ostatok;
				temp_arr_counter++;

				ostatok = temp % system;
				temp = temp / system;
			}
			temp_arr[temp_arr_counter] = ostatok;
			temp_arr_counter++;
			temp_arr[temp_arr_counter] = temp;
		}

		splitter++;
		for (int j = temp_arr_counter; j >= 0; j--) {
			if (temp_arr[j] < 10)
				printf("%d", temp_arr[j]);
			else
				printf("%c", returnChar(temp_arr[j]));
			if (j == 0 && splitter < 4)
				printf("%s", ".");
		}

	}
	puts("");
	return 0;
}

char returnChar(int number) {
	switch (number) {
	case 10:
		return 'A';
		break;
	case 11:
		return 'B';
		break;
	case 12:
		return 'C';
		break;
	case 13:
		return 'D';
		break;
	case 14:
		return 'E';
		break;
	case 15:
		return 'F';
		break;
	default:
		return '0';
		break;
	}
}