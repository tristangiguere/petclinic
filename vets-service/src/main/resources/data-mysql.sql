INSERT IGNORE INTO vets VALUES (1, 234568, 'James', 'Carter', 'carter.james@email.com', '(514)-634-8276 #2384','profilepic1.png', 'Practicing since 3 years', 'Monday, Tuesday, Friday', 1);
INSERT IGNORE INTO vets VALUES (2, 327874, 'Helen', 'Leary', 'leary.helen@email.com', '(514)-634-8276 #2385','profilepic2.png','Practicing since 10 years', 'Wednesday, Thursday', 1);
INSERT IGNORE INTO vets VALUES (3, 238372, 'Linda', 'Douglas', 'douglas.linda@email.com', '(514)-634-8276 #2386','profilepic3.png', 'Practicing since 5 years', 'Monday, Wednesday, Thursday', 1);
INSERT IGNORE INTO vets VALUES (4, 823097, 'Rafael', 'Ortega', 'ortega.rafael@email.com', '(514)-634-8276 #2387','profilepic4.png' ,'Practicing since 8 years', 'Wednesday, Thursday, Friday', 0);
INSERT IGNORE INTO vets VALUES (5, 842370, 'Henry', 'Stevens', 'stevens.henry@email.com', '(514)-634-8276 #2389','profilepic5.png' ,'Practicing since 1 years', 'Monday, Tuesday, Wednesday, Thursday', 0);
INSERT IGNORE INTO vets VALUES (6, 784233, 'Sharon', 'Jenkins', 'jenkins.sharon@email.com', '(514)-634-8276 #2383','profilepic6.png', 'Practicing since 6 years', 'Monday, Tuesday, Friday', 0);

INSERT IGNORE INTO specialties VALUES (1, 'radiology');
INSERT IGNORE INTO specialties VALUES (2, 'surgery');
INSERT IGNORE INTO specialties VALUES (3, 'dentistry');

INSERT IGNORE INTO vet_specialties VALUES (2, 1);
INSERT IGNORE INTO vet_specialties VALUES (3, 2);
INSERT IGNORE INTO vet_specialties VALUES (3, 3);
INSERT IGNORE INTO vet_specialties VALUES (4, 2);
INSERT IGNORE INTO vet_specialties VALUES (5, 1);