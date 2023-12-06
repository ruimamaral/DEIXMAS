# DEIXMAS Database

DEIXMAS's backend must have a Database available to persist data. This solution uses a simple `docker-compose.yml` file to manage a containerized MariaDB database.

## Starting

Run, in this directory:

```bash
sudo docker-compose up -d --build
```

## Stopping

Run, in this directory:

```bash
sudo docker-compose down
```

NOTE: In some systems, you may need to replace `docker-compose` with `docker compose` in the commands above.

## Connecting manually

Although you generally will never need to connect to the database manually, you can do so by running:

```bash
mysql -P <PORT> -u dei_xmas -p dei_xmas
```

and entering the database password for `dei_xmas` when prompted.
