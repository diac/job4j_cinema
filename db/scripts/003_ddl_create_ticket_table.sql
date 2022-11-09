CREATE TABLE ticket (
    id SERIAL PRIMARY KEY,
    session_id INT NOT NULL REFERENCES sessions(id),
    pos_row INT NOT NULL,
    cell INT NOT NULL,
    user_id INT NOT NULL REFERENCES users(id),
    CONSTRAINT UTicket_Session_Place UNIQUE (session_id, pos_row, cell)
);

COMMENT ON TABLE ticket IS 'Билеты. Каждый билет связывает пользователя с сеансом.';