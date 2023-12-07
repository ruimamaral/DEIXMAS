FROM node:19
WORKDIR /usr/app

COPY package*.json .
RUN npm install

COPY . .

CMD ["npm", "run", "dev", "--", "--host"]
